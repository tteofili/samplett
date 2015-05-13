package com.github.tteofili.looseen;

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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.classification.Classifier;
import org.apache.lucene.classification.KNearestNeighborClassifier;
import org.apache.lucene.classification.utils.ConfusionMatrixGenerator;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.index.StorableField;
import org.apache.lucene.index.StoredDocument;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.Test;


public final class TestLuceneIndexClassifier extends LuceneTestCase {

    private static final String INDEX = "/Users/teofili/Desktop/itwiki/index";
    private static final List<String> CATEGORIES = new LinkedList<>();
    private static final Pattern pattern = Pattern.compile("\\[Categoria\\:(.+)\\|\\s");
    private static final boolean index = true;

    @Test
    public void testItalianWikipedia() throws Exception {
        Path path = Paths.get(INDEX);
        Directory directory = FSDirectory.open(path);
        IndexReader reader = null;
        try {
            Collection<String> stopWordsList = Arrays.asList("di", "a", "da", "in", "per", "tra", "fra", "il", "lo", "la", "i", "gli", "le");
            CharArraySet stopWords = new CharArraySet(stopWordsList, true);
            Analyzer analyzer = new ItalianAnalyzer(stopWords);
            if (index) {

                System.out.format("Indexing Italian Wikipedia...%n");
                IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));

                indexWriter.deleteAll();
                indexWriter.commit();

                importWikipedia(new File("/path/to/itwiki-20150405-pages-meta-current1.xml"), indexWriter);

                indexWriter.forceMerge(3);
                indexWriter.close();

                System.gc();
            }

            // get the categories
            System.out.format("Reading categories...%n");
            reader = DirectoryReader.open(directory);

            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs topDocs = searcher.search(new WildcardQuery(new Term("cat", "*")), Integer.MAX_VALUE);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                StoredDocument doc = searcher.doc(scoreDoc.doc);
                StorableField cat = doc.getField("cat");
                if (cat != null && !CATEGORIES.contains(cat.stringValue())) {
                    CATEGORIES.add(cat.stringValue());
                    System.out.println("'" + cat.stringValue() + "'");
                }
            }

            final long startTime = System.currentTimeMillis();

            LeafReader ar = SlowCompositeReaderWrapper.wrap(reader);

            Classifier<BytesRef> classifier = new KNearestNeighborClassifier(ar, analyzer, null, 1, 0, 0, "cat", "text");
//            Classifier<BytesRef> classifier = new SimpleNaiveBayesClassifier(ar, analyzer, null, "cat", "text");
//            Classifier<BytesRef> classifier = new CachingNaiveBayesClassifier(ar, analyzer, null, "cat", "text");
//            Classifier<BytesRef> classifier = new BooleanPerceptronClassifier(ar, analyzer, null, 1, null, "cat", "text");


            System.out.format("Starting evaluation...%n");

            final int maxdoc = reader.maxDoc();

            ConfusionMatrixGenerator.ConfusionMatrix confusionMatrix = ConfusionMatrixGenerator.getConfusionMatrix(ar, classifier, "cat", "text");
            System.out.println("confusion matrix");
            System.out.format("%s", confusionMatrix.toString());

            final long endTime = System.currentTimeMillis();
            final int elapse = (int) (endTime - startTime) / 1000;

            // print results
            int fc = 0, tc = 0;

            Map<String, Map<String, Long>> linearizedMatrix = confusionMatrix.getLinearizedMatrix();

            System.out.format("Creating report...%n");
            for (Map.Entry<String, Map<String, Long>> entry : linearizedMatrix.entrySet()) {
                String correctAnswer = entry.getKey();
                for (Map.Entry<String, Long> classifiedAnswers : entry.getValue().entrySet()) {
                    if (correctAnswer.equals(classifiedAnswers.getKey())) {
                        tc += classifiedAnswers.getValue();
                    } else {
                        fc += classifiedAnswers.getValue();
                    }
                }

            }
            float accrate = (float) tc / (float) (tc + fc);
            float errrate = (float) fc / (float) (tc + fc);
            System.out.printf("\n\n*** accuracy rate = %f, error rate = %f; time = %d (sec); %d docs\n", accrate, errrate, elapse, maxdoc);

        } finally {
            if (reader != null) {
                reader.close();
            }
            directory.close();
        }
    }

    private static void importWikipedia(File dump, IndexWriter indexWriter) throws Exception {
        long start = System.currentTimeMillis();
        int count = 0;
        System.out.format("Importing %s...%n", dump);

        String title = null;
        String text = null;
        Set<String> cats = new HashSet<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        StreamSource source;
        if (dump.getName().endsWith(".xml")) {
            source = new StreamSource(dump);
        } else {
            throw new RuntimeException("can index only wikipedia XML files");
//            CompressorStreamFactory csf = new CompressorStreamFactory();
//            source = new StreamSource(csf.createCompressorInputStream(
//                    new BufferedInputStream(new FileInputStream(dump))));
        }
        XMLStreamReader reader = factory.createXMLStreamReader(source);
        while (reader.hasNext()) {
            if (count == 30000) {
                break;
            }
            switch (reader.next()) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("title".equals(reader.getLocalName())) {
                        title = reader.getElementText();
                    } else if ("text".equals(reader.getLocalName())) {
                        text = reader.getElementText();
                        Matcher matcher = pattern.matcher(text);
                        int pos = 0;
                        while (matcher.find(pos)) {
                            cats.add(matcher.group(1));
                            pos = matcher.end();
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if ("page".equals(reader.getLocalName())) {
                        Document page = new Document();
                        if (title != null) {
                            page.add(new TextField("title", title, StoredField.Store.YES));
                        }
                        if (text != null) {
                            page.add(new TextField("text", text, StoredField.Store.YES));
                        }
                        for (String cat : cats) {
                            page.add(new StringField("cat", cat, StoredField.Store.YES));
                        }
                        cats.clear();
                        indexWriter.addDocument(page);
                        count++;
                        if (count % 100000 == 0) {
                            indexWriter.commit();
                            System.out.format("Committed %d pages%n", count);
                        }
                    }
                    break;
            }
        }

        indexWriter.commit();

        long millis = System.currentTimeMillis() - start;
        System.out.format(
                "Imported %d pages in %d seconds (%.2fms/page)%n",
                count, millis / 1000, (double) millis / count);
    }


}