package com.github.tteofili.looseen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.github.tteofili.looseen.yay.SGM;
import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.geo.GeoUtils;
import org.apache.lucene.geo.Polygon;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.spatial.geopoint.document.GeoPointField;
import org.apache.lucene.spatial.geopoint.search.GeoPointDistanceQuery;
import org.apache.lucene.spatial.geopoint.search.GeoPointInBBoxQuery;
import org.apache.lucene.spatial.geopoint.search.GeoPointInPolygonQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;

/**
 * Tests for using word vectors in Lucene
 */
public class IndexingWordVectorsTest {

    private static final String PREFIX = "/Users/teofili/data";
    private static final String INDEX = PREFIX + "/20n/index";
    private static final String BODY_FIELD = "body";
    private static final String SUBJECT_FIELD = "subject";
    private static final String WV_FIELD = "wv";
    public static final String TERM_FIELD = "term";
    public static final String CATEGORY_FIELD = "category";
    private boolean index = false;

    @Test
    public void testWVtoGeoIndexAndSearch() throws Exception {

        Path mainIndexPath = Paths.get(INDEX + "/original");
        Directory directory = FSDirectory.open(mainIndexPath);

        Path wvPath = Paths.get(INDEX + "/wv");
        if (index) {
            delete(wvPath);
        }

        Directory wvIndex = FSDirectory.open(wvPath);

        IndexWriter indexWriter = new IndexWriter(wvIndex, new IndexWriterConfig());
        IndexReader reader = DirectoryReader.open(directory);
        try {
            if (index) {

                long indexingStartTime = System.currentTimeMillis();

                int md = reader.maxDoc();

                for (int i = 0; i < md; i++) {
                    Document doc = reader.document(i);

                    IndexableField textField = doc.getField(BODY_FIELD);

                    IndexableField classField = doc.getField(CATEGORY_FIELD);

                    SGM network = null;
                    try {
                        network = SGM.newModel().
                                fromText(textField.stringValue()).
                                withWindow(3).
                                withDimension(2).
                                withAlpha(0.2).
                                withLambda(0.0001).
                                useNesterovMomentum(true).
                                withMu(0.9).
                                withMaxIterations(100).
                                withBatchSize(1).build();
                    } catch (Throwable e) {
                        System.err.println(e.getLocalizedMessage() + "... skipping");
                    }
                    if (network != null) {
                        RealMatrix wv = network.getWeights()[0];
                        List<String> vocabulary = network.getVocabulary();

                        index(classField.stringValue(), vocabulary, wv, indexWriter);
                    }
                    if (i % 100 == 0) {
                        System.out.println("processed " + i + " docs");
                    }
                }
                indexWriter.commit();

                reader.close();


                long indexingEndTime = System.currentTimeMillis();

                System.out.println("word vectors indexed in " + (indexingEndTime - indexingStartTime) / 1000 + " seconds");
            }

            reader = DirectoryReader.open(wvIndex);

            // search
            Analyzer analyzer = new StandardAnalyzer();

            String[] queries = new String[3];
            queries[0] = "Please enlighten me"; // rep.motorcycles
            queries[1] = "The primary objective of the"; // sci.space
            queries[2] = "glad to see that you've admitted"; // talk.politics.misc

            IndexSearcher indexSearcher = new IndexSearcher(reader);
            for (String s : queries) {
                Collection<String> result = new LinkedList<>();
                try (TokenStream tokenStream = analyzer.tokenStream(TERM_FIELD, s)) {
                    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                    tokenStream.reset();
                    while (tokenStream.incrementToken()) {
                        result.add(charTermAttribute.toString());
                    }
                    tokenStream.end();
                }
                for (String r : result) {
                    TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(TERM_FIELD, r)), 3);
                    // get wv for this term, grouped by class

                    ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                    for (ScoreDoc scoreDoc : scoreDocs) {
                        Document document = reader.document(scoreDoc.doc);
                        IndexableField classField = document.getField(CATEGORY_FIELD);
                        IndexableField[] wvdFields = document.getFields(WV_FIELD);

                        Map<String, Long> classes = new HashMap<>();

                        double[] lats = new double[wvdFields.length];
                        double[] lons = new double[wvdFields.length];
                        int i = 0;
                        for (IndexableField f : wvdFields) {
                            long v = f.numericValue().longValue();
                            try {
                                double x = GeoPointField.decodeLatitude(v);
                                double y = GeoPointField.decodeLongitude(v);
                                lats[i] = x;
                                lons[i] = y;
                                i++;

                                GeoPointDistanceQuery query = new GeoPointDistanceQuery(WV_FIELD, x, y, 10);

                                TopDocs search = indexSearcher.search(query, 10);
                                for (ScoreDoc sd : search.scoreDocs) {
                                    Document d = reader.document(sd.doc);
                                    System.err.println(d.getField(TERM_FIELD).stringValue() + " is near to " + r);

                                    String key = d.getField(CATEGORY_FIELD).stringValue();
                                    Long aLong = classes.get(key);
                                    if (aLong == null) {
                                        aLong = 0L;
                                    }
                                    aLong++;
                                    classes.put(key, aLong);
                                }
                            } catch (Exception e) {
                                System.err.println(e.getLocalizedMessage());
                            }
                        }
                        try {
                            if (lats.length >= 4 && lons.length >= 4) {
                                Query q = new GeoPointInPolygonQuery(WV_FIELD, new Polygon(lats, lons));
                                TopDocs search = indexSearcher.search(q, 10);
                                for (ScoreDoc sd : search.scoreDocs) {
                                    Document d = reader.document(sd.doc);
                                    System.err.println(d.getField(TERM_FIELD).stringValue() + " is p-contained in " + r);
                                }
                            } else if (lats.length == 2 && lons.length == 2) {
                                Query q = new GeoPointInBBoxQuery(WV_FIELD, Math.min(lats[0], lats[1]), Math.max(lats[0], lats[1]),
                                        Math.min(lons[0], lons[1]), Math.max(lons[0], lons[1]));
                                TopDocs search = indexSearcher.search(q, 10);
                                for (ScoreDoc sd : search.scoreDocs) {
                                    Document d = reader.document(sd.doc);
                                    System.err.println(d.getField(TERM_FIELD).stringValue() + " is bb-contained in " + r);
                                }
                            }
                        } catch (Exception e) {
                            System.err.println(e.getLocalizedMessage());
                        }
                        // compare extracted classes and assigned class
                        System.out.println(classes + " -> " + classField.stringValue());
                    }

                }
            }

        } finally {
            if (reader != null) {
                reader.close();
            }
            indexWriter.close();
            wvIndex.close();
            directory.close();
        }
    }

    private void index(String category, List<String> vocabulary, RealMatrix wv, IndexWriter writer) throws IOException {
        for (int i = 0; i < wv.getColumnDimension(); i++) {
            double[] a = wv.getColumnVector(i).toArray();
            String term = vocabulary.get(i);
            Document document = new Document();
            document.add(new TextField(TERM_FIELD, term, Field.Store.YES));
            document.add(new TextField(CATEGORY_FIELD, category, Field.Store.YES));
            double latitude = a[0];
            double longitude = a[1];

            // check / adjust lat / lon
            try {
                GeoUtils.checkLatitude(latitude);
            } catch (IllegalArgumentException e) {
                latitude = latitude % 90;
            }

            try {
                GeoUtils.checkLongitude(longitude);
            } catch (IllegalArgumentException e) {
                longitude = longitude % 180;
            }

            document.add(new GeoPointField(WV_FIELD, latitude, longitude, Field.Store.YES));

            writer.addDocument(document);
        }
    }

    private void delete(Path... paths) throws IOException {
        for (Path path : paths) {
            if (Files.isDirectory(path)) {
                Stream<Path> pathStream = Files.list(path);
                Iterator<Path> iterator = pathStream.iterator();
                while (iterator.hasNext()) {
                    Files.delete(iterator.next());
                }
            }
        }

    }

    void buildIndex(File indexDir, IndexWriter indexWriter)
            throws IOException {
        File[] groupsDir = indexDir.listFiles();
        if (groupsDir != null) {
            for (File group : groupsDir) {
                String groupName = group.getName();
                File[] posts = group.listFiles();
                if (posts != null) {
                    for (File postFile : posts) {
                        String number = postFile.getName();
                        NewsPost post = parse(postFile, groupName, number);
                        Document d = new Document();
                        d.add(new StringField(CATEGORY_FIELD,
                                post.getGroup(), Field.Store.YES));
                        d.add(new SortedDocValuesField(CATEGORY_FIELD,
                                new BytesRef(post.getGroup())));
                        d.add(new TextField(SUBJECT_FIELD,
                                post.getSubject(), Field.Store.YES));
                        d.add(new TextField(BODY_FIELD,
                                post.getBody(), Field.Store.YES));
                        indexWriter.addDocument(d);
                    }
                }
            }
        }
        indexWriter.commit();
    }

    private NewsPost parse(File postFile, String groupName, String number) throws IOException {
        StringBuilder body = new StringBuilder();
        String subject = "";
        FileInputStream stream = new FileInputStream(postFile);
        boolean inBody = false;
        for (String line : IOUtils.readLines(stream)) {
            if (line.startsWith("Subject:")) {
                subject = line.substring(8);
            } else {
                if (inBody) {
                    if (body.length() > 0) {
                        body.append("\n");
                    }
                    body.append(line);
                } else if (line.isEmpty() || line.trim().length() == 0) {
                    inBody = true;
                }
            }
        }
        return new NewsPost(body.toString(), subject, groupName, number);
    }

    private class NewsPost {
        private final String body;
        private final String subject;
        private final String group;
        private final String number;

        private NewsPost(String body, String subject, String group, String number) {
            this.body = body;
            this.subject = subject;
            this.group = group;
            this.number = number;
        }

        public String getBody() {
            return body;
        }

        public String getSubject() {
            return subject;
        }

        public String getGroup() {
            return group;
        }

        public String getNumber() {
            return number;
        }
    }

}
