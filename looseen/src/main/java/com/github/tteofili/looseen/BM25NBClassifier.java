/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tteofili.looseen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.Classifier;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.util.BytesRef;

/**
 * A classifier approximating naive bayes by using pure queries based on probabilistic Okapi BM25 model.
 *
 * @lucene.experimental
 */
public class BM25NBClassifier implements Classifier<BytesRef> {

    /**
     * {@link IndexReader} used to access the {@link Classifier}'s
     * index
     */
    protected final IndexReader indexReader;

    /**
     * names of the fields to be used as input text
     */
    protected final String[] textFieldNames;

    /**
     * name of the field to be used as a class / category output
     */
    protected final String classFieldName;

    /**
     * {@link Analyzer} to be used for tokenizing unseen input text
     */
    protected final Analyzer analyzer;

    /**
     * {@link IndexSearcher} to run searches on the index for retrieving frequencies
     */
    protected final IndexSearcher indexSearcher;

    /**
     * {@link Query} used to eventually filter the document set to be used to classify
     */
    protected final Query query;

    private final int ngramSize;

    /**
     * Creates a new NaiveBayes classifier.
     *
     * @param indexReader     the reader on the index to be used for classification
     * @param analyzer       an {@link Analyzer} used to analyze unseen text
     * @param query          a {@link Query} to eventually filter the docs used for training the classifier, or {@code null}
     *                       if all the indexed docs should be used
     * @param classFieldName the name of the field used as the output for the classifier NOTE: must not be havely analyzed
     *                       as the returned class will be a token indexed for this field
     * @param textFieldNames the name of the fields used as the inputs for the classifier, NO boosting supported per field
     */
    public BM25NBClassifier(IndexReader indexReader, Analyzer analyzer, Query query, int ngramSize, String classFieldName, String... textFieldNames) {
        this.indexReader = indexReader;
        this.indexSearcher = new IndexSearcher(this.indexReader);
        this.indexSearcher.setSimilarity(new BM25Similarity());
        this.textFieldNames = textFieldNames;
        this.classFieldName = classFieldName;
        this.analyzer = analyzer;
        this.query = query;
        this.ngramSize = ngramSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassificationResult<BytesRef> assignClass(String inputDocument) throws IOException {
        return assignClassNormalizedList(inputDocument).get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClassificationResult<BytesRef>> getClasses(String text) throws IOException {
        List<ClassificationResult<BytesRef>> assignedClasses = assignClassNormalizedList(text);
        Collections.sort(assignedClasses);
        return assignedClasses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClassificationResult<BytesRef>> getClasses(String text, int max) throws IOException {
        List<ClassificationResult<BytesRef>> assignedClasses = assignClassNormalizedList(text);
        Collections.sort(assignedClasses);
        return assignedClasses.subList(0, max);
    }

    /**
     * Calculate probabilities for all classes for a given input text
     * @param inputDocument the input text as a {@code String}
     * @return a {@code List} of {@code ClassificationResult}, one for each existing class
     * @throws IOException if assigning probabilities fails
     */
    protected List<ClassificationResult<BytesRef>> assignClassNormalizedList(String inputDocument) throws IOException {
        List<ClassificationResult<BytesRef>> assignedClasses = new ArrayList<>();

        Terms classes = MultiFields.getTerms(indexReader, classFieldName);
        TermsEnum classesEnum = classes.iterator();
        BytesRef next;
        Collection<String[]> ngrams = tokenize(inputDocument, ngramSize);
        while ((next = classesEnum.next()) != null) {
            if (next.length > 0) {
                Term term = new Term(this.classFieldName, next);
                double prior = calculateLogPrior(term);
                double likelihood = calculateLogLikelihood(ngrams, term);
                double clVal = prior + likelihood;
                assignedClasses.add(new ClassificationResult<>(term.bytes(), clVal));
            }
        }

        // sort by score
        Collections.sort(assignedClasses);
        return assignedClasses;
    }

    /**
     * tokenize a <code>String</code> on this classifier's text fields and analyzer
     *
     * @param text the <code>String</code> representing an input text (to be classified)
     * @return a <code>String</code> array of the resulting tokens
     * @throws IOException if tokenization fails
     */
    protected Collection<String[]> tokenize(String text, int ngramSize) throws IOException {
        Collection<String> result = new LinkedList<>();
        for (String textFieldName : textFieldNames) {
            try (TokenStream tokenStream = analyzer.tokenStream(textFieldName, text)) {
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                tokenStream.reset();
                while (tokenStream.incrementToken()) {
                    result.add(charTermAttribute.toString());
                }
                tokenStream.end();
            }
        }
        return getNgrams(result.toArray(new String[result.size()]), ngramSize);
    }

    private double calculateLogLikelihood(Collection<String[]> ngrams, Term term) throws IOException {
        double result = 0d;
        for (String[] words : ngrams) {
            result += Math.log(getTermProbForClass(term, words));
        }
        return result;
    }

    private Collection<String[]> getNgrams(String[] sequence, int size) {
        Collection<String[]> ngrams = new LinkedList<>();
        if (size == -1 || size >= sequence.length) {
            ngrams.add(sequence);
        } else {
            for (int i = 0; i < sequence.length - size + 1; i++) {
                String[] ngram = new String[size];
                ngram[0] = sequence[i];
                for (int j = 1; j < size; j++) {
                    ngram[j] = sequence[i + j];
                }
                ngrams.add(ngram);
            }
        }
        return ngrams;
    }

    private double getTermProbForClass(Term term, String... words) throws IOException {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(new BooleanClause(new TermQuery(term), BooleanClause.Occur.MUST));
        for (String textFieldName : textFieldNames) {
            for (String word : words) {
                builder.add(new BooleanClause(new TermQuery(new Term(textFieldName, word)), BooleanClause.Occur.SHOULD));
            }
        }
        TopDocs search = indexSearcher.search(builder.build(), 1);
        return search.totalHits > 0 ? search.getMaxScore() : 1;
    }

    private double calculateLogPrior(Term term) throws IOException {
        TopDocs topDocs = indexSearcher.search(new TermQuery(term), 1);
        return topDocs.totalHits > 0 ? Math.log(topDocs.getMaxScore()) : 0;
    }

    @Override
    public String toString() {
        return "BM25NBClassifier{" +
                "similarity=" + indexSearcher.getSimilarity(true) +
                ", ngramSize=" + ngramSize +
                '}';
    }
}
