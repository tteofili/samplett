package com.github.tteofili.looseen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.minhash.MinHashFilter;
import org.apache.lucene.analysis.minhash.MinHashFilterFactory;
import org.apache.lucene.analysis.shingle.ShingleFilterFactory;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.Classifier;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ConstantScoreQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

/**
 * a {@link Classifier} based on LSH via queries on in memory sidecar index using {@link MinHashFilter} to index passed
 * reader's docs.
 */
public class MinHashClassifier implements Classifier<BytesRef> {

    private final RAMDirectory directory;
    private final int min;
    private final int hashCount;
    private final int hashSize;

    public MinHashClassifier(IndexReader reader, String textField, String categoryField, int min, int hashCount,
                             int hashSize) {
        this.min = min;
        this.hashCount = hashCount;
        this.hashSize = hashSize;
        try {
            Analyzer analyzer = createMinHashAnalyzer(min, hashCount, hashSize);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            directory = new RAMDirectory();
            IndexWriter writer = new IndexWriter(directory, config);
            for (int i = 0; i < reader.maxDoc(); i++) {
                Document document = new Document();
                Document d = reader.document(i);
                String textValue = d.getField(textField).stringValue();
                String categoryValue = d.getField(categoryField).stringValue();
                document.add(new TextField("text", textValue, Field.Store.NO));
                document.add(new StringField("category", categoryValue, Field.Store.YES));
                writer.addDocument(document);
            }
            writer.commit();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
    }

    @Override
    public ClassificationResult<BytesRef> assignClass(String text) throws IOException {
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        try {
            TopDocs topDocs = searcher.search(buildQuery("text", text, min, hashCount, hashSize), 1);
            if (topDocs.totalHits > 0) {
                Document document = reader.document(topDocs.scoreDocs[0].doc);
                String category = document.getField("category").stringValue();
                return new ClassificationResult<>(new BytesRef(category), topDocs.getMaxScore());
            } else {
                return null;
            }
        } finally {
            reader.close();
        }
    }

    @Override
    public List<ClassificationResult<BytesRef>> getClasses(String text) throws IOException {
        return null;
    }

    @Override
    public List<ClassificationResult<BytesRef>> getClasses(String text, int max) throws IOException {
        return null;
    }

    public static Analyzer createMinHashAnalyzer(int min, int hashCount, int hashSetSize) throws IOException {
        Map<String, String> sffargs = new HashMap<>();
        sffargs.put("minShingleSize", "" + min);
        sffargs.put("maxShingleSize", "" + min);
        sffargs.put("outputUnigrams", "false");
        sffargs.put("outputUnigramsIfNoShingles", "false");
        sffargs.put("tokenSeparator", " ");
        HashMap<String, String> lshffargs = new HashMap<>();
        lshffargs.put("hashCount", "" + hashCount);
        lshffargs.put("hashSetSize", "" + hashSetSize);
        CustomAnalyzer.Builder builder = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(ShingleFilterFactory.class, sffargs)
                .addTokenFilter(MinHashFilterFactory.class, lshffargs);

        return builder.build();
    }

    private Query buildQuery(String field, String query, int min, int hashCount, int hashSetSize) throws IOException {
        Analyzer chain = createMinHashAnalyzer(min, hashCount, hashSetSize);
        ArrayList<String> tokens = getTokens(chain, field, query);
        chain.close();
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        for (String token : tokens) {
            builder.add(new ConstantScoreQuery(new TermQuery(new Term("text", token))), BooleanClause.Occur.SHOULD);
        }
        return builder.build();
    }

    private ArrayList<String> getTokens(Analyzer analyzer, String field, String value) throws IOException {
        ArrayList<String> tokens = new ArrayList<String>();
        TokenStream ts = analyzer.tokenStream(field, value);
        ts.reset();
        while (ts.incrementToken()) {
            CharTermAttribute termAttribute = ts.getAttribute(CharTermAttribute.class);
            String token = new String(termAttribute.buffer(), 0, termAttribute.length());
            tokens.add(token);
        }
        ts.end();
        ts.close();
        return tokens;
    }

    @Override
    public String toString() {
        return "MinHashClassifier{" +
                "min=" + min +
                ", hashCount=" + hashCount +
                ", hashSize=" + hashSize +
                '}';
    }
}
