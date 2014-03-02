package com.github.tteofili.looseen;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.Classifier;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;

/**
 * Add javadoc here
 */
public class LuceneRandomForestClassifier implements Classifier<BytesRef> {
    @Override
    public ClassificationResult<BytesRef> assignClass(String text) throws IOException {
        return null;
    }

    @Override
    public void train(AtomicReader atomicReader, String textFieldName, String classFieldName, Analyzer analyzer) throws IOException {
    }

    @Override
    public void train(AtomicReader atomicReader, String textFieldName, String classFieldName, Analyzer analyzer, Query query) throws IOException {
    }

    @Override
    public void train(AtomicReader atomicReader, String[] textFieldNames, String classFieldName, Analyzer analyzer, Query query) throws IOException {

    }
}
