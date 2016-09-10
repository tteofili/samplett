package com.github.tteofili.looseen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.spatial.geopoint.document.GeoPointField;
import org.apache.lucene.spatial.geopoint.search.GeoPointDistanceQuery;
import org.apache.lucene.spatial.geopoint.search.GeoPointInBBoxQuery;
import org.apache.lucene.spatial.geopoint.search.GeoPointInPolygonQuery;
import org.apache.lucene.spatial3d.Geo3DPoint;
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

    // 20n fields
    private static final String BODY_FIELD = "body";
    private static final String SUBJECT_FIELD = "subject";

    // wvIndex fields
    private static final String WV_FIELD = "wv";
    private static final String DOCID_FIELD = "docid";
    private static final String TERM_FIELD = "term";
    private static final String CATEGORY_FIELD = "category";

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
                performIndexing(indexWriter, reader);
            }

            reader = DirectoryReader.open(wvIndex);

            // search
            Analyzer analyzer = new StandardAnalyzer();

            String[] queries = new String[4];
            queries[0] = "Please enlighten me"; // rep.motorcycles
            queries[1] = "The primary objective of the"; // sci.space
            queries[2] = "glad to see that you've admitted"; // talk.politics.misc
            queries[3] = "I think is what it means"; // comp.graphics

            IndexSearcher indexSearcher = new IndexSearcher(reader);
            for (String s : queries) {
                // for a certain query

                Collection<String> result = new LinkedList<>();
                try (TokenStream tokenStream = analyzer.tokenStream(TERM_FIELD, s)) {
                    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                    tokenStream.reset();
                    while (tokenStream.incrementToken()) {
                        result.add(charTermAttribute.toString());
                    }
                    tokenStream.end();
                }
                // for each term in the query
                List<Polygon> ps = new LinkedList<>();
                List<Query> bbqs = new LinkedList<>();
                for (String r : result) {

                    TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(TERM_FIELD, r)), 3);

                    // each result is a term with the given text, a class and lat,lon

                    ScoreDoc[] scoreDocs = topDocs.scoreDocs;

                    List<Double> lts = new LinkedList<>();
                    List<Double> lgs = new LinkedList<>();

                    for (ScoreDoc scoreDoc : scoreDocs) {
                        Document document = reader.document(scoreDoc.doc);
                        IndexableField classField = document.getField(CATEGORY_FIELD);

                        // get term occurrence's word vectors
                        IndexableField[] wvdFields = document.getFields(WV_FIELD);


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

                                lts.add(x);
                                lgs.add(y);
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

                        if (lats.length > 1) {
                            Query query = Geo3DPoint.newPathQuery(WV_FIELD, lats, lons, 1);
                            TopDocs docs = indexSearcher.search(query, 10);

                            for (ScoreDoc sd : docs.scoreDocs) {
                                Document d = reader.document(sd.doc);
                                System.err.println(d.getField(TERM_FIELD).stringValue() + " is near the query" + query);
                            }
                        }
                    }

                    if (lts.size() >= 4) {
                        double[] l1 = new double[lts.size()];
                        double[] l2 = new double[lgs.size()];

                        for (int i = 0; i < lts.size(); i++) {
                            l1[i] = lts.get(i);
                            l2[i] = lgs.get(i);
                        }

                        ps.add(new Polygon(l1, l2));

                    } else if (lts.size() >= 2) {
                        Collections.sort(lts);
                        double minLat = lts.get(0);
                        double maxLat = lts.get(lts.size() - 1);

                        Collections.sort(lgs);
                        double minLong = lgs.get(0);
                        double maxLong = lgs.get(lgs.size() - 1);

                        bbqs.add(new GeoPointInBBoxQuery(WV_FIELD, minLat, maxLat, minLong, maxLong));
                    }

                }

                if (ps.size() > 0) {
                    Query q = new GeoPointInPolygonQuery(WV_FIELD, ps.toArray(new Polygon[ps.size()]));
                    TopDocs search = indexSearcher.search(q, 10);
                    System.err.println("query " + result.toString() + " encoded as " + q + " matched " + search.totalHits + " docs");
                    for (ScoreDoc sd : search.scoreDocs) {
                        Document d = reader.document(sd.doc);
                        System.err.println("match " + d);
                    }

                    for (Polygon polygon : ps) {
                        Query query = Geo3DPoint.newPathQuery(WV_FIELD, polygon.getPolyLats(), polygon.getPolyLons(), 1);
                        TopDocs docs = indexSearcher.search(query, 10);

                        for (ScoreDoc sd : docs.scoreDocs) {
                            Document d = reader.document(sd.doc);
                            System.err.println(d.getField(TERM_FIELD).stringValue() + " is near polygon path " + query);
                        }
                    }
                }

                if (bbqs.size() > 0) {
                    BooleanQuery.Builder builder = new BooleanQuery.Builder();
                    for (Query q : bbqs) {
                        builder.add(new BooleanClause(q, BooleanClause.Occur.MUST));
                    }
                    BooleanQuery bbq = builder.build();
                    TopDocs search = indexSearcher.search(bbq, 10);
                    System.err.println("bb query " + bbq + " matched " + search.totalHits + " docs");
                    for (ScoreDoc sd : search.scoreDocs) {
                        Document d = reader.document(sd.doc);
                        System.err.println("match " + d);
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

    @Test
    public void testWVSimpleTermProximity() throws Exception {
        /* search for each term in the query individually, retrieve each term's wvs, search for nearest points and
         aggregate the related classes */

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
                performIndexing(indexWriter, reader);
            }

            reader = DirectoryReader.open(wvIndex);

            // search
            Analyzer analyzer = new StandardAnalyzer();

            String[] queries = new String[4];
            queries[0] = "Please enlighten me"; // rep.motorcycles
            queries[1] = "The primary objective of the"; // sci.space
            queries[2] = "glad to see that you've admitted"; // talk.politics.misc
            queries[3] = "I think is what it means"; // comp.graphics

            IndexSearcher indexSearcher = new IndexSearcher(reader);
            for (String s : queries) {
                System.err.println("****");
                // for a certain query

                Collection<String> result = new LinkedList<>();
                try (TokenStream tokenStream = analyzer.tokenStream(TERM_FIELD, s)) {
                    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                    tokenStream.reset();
                    while (tokenStream.incrementToken()) {
                        result.add(charTermAttribute.toString());
                    }
                    tokenStream.end();
                }
                Map<String, Long> classes = new HashMap<>();
                // for each term in the query
                for (String r : result) {

                    TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(TERM_FIELD, r)), 100);

                    // each result is a term with the given text, a class and lat,lon

                    ScoreDoc[] scoreDocs = topDocs.scoreDocs;

                    for (ScoreDoc scoreDoc : scoreDocs) {
                        Document document = reader.document(scoreDoc.doc);

                        // get term occurrence's word vectors
                        IndexableField[] wvdFields = document.getFields(WV_FIELD);

                        int i = 0;
                        for (IndexableField f : wvdFields) {
                            long v = f.numericValue().longValue();
                            try {
                                double x = GeoPointField.decodeLatitude(v);
                                double y = GeoPointField.decodeLongitude(v);
                                i++;

                                GeoPointDistanceQuery query = new GeoPointDistanceQuery(WV_FIELD, x, y, 7);

                                // find terms close to this term with their assigned classes in order to find this terms's class
                                TopDocs search = indexSearcher.search(query, 3);
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
                    }

                }
                System.err.println(classes);
                System.err.println("*********************");
                System.err.println("*********************");
                System.err.println("*********************");
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

    private void performIndexing(IndexWriter indexWriter, IndexReader reader) throws IOException {
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
                        withAlpha(0.05).
                        withLambda(0.0001).
                        useNesterovMomentum().
                        withMu(0.8).
                        withMaxIterations(100).
                        withBatchSize(1).build();
            } catch (Throwable e) {
                System.err.println(e.getLocalizedMessage() + "... skipping");
            }
            if (network != null) {
                RealMatrix wv = network.getWeights()[0];
                List<String> vocabulary = network.getVocabulary();

                index(classField.stringValue(), vocabulary, wv, indexWriter, i);
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

    @Test
    public void testWVQueryCentroidsSearch() throws Exception {

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
                performIndexing(indexWriter, reader);
            }

            reader = DirectoryReader.open(wvIndex);

            // search
            Analyzer analyzer = new StandardAnalyzer();

            String[] queries = new String[4];
            queries[0] = "Please enlighten me"; // rep.motorcycles
            queries[1] = "The primary objective of the"; // sci.space
            queries[2] = "glad to see that you've admitted"; // talk.politics.misc
            queries[3] = "I think is what it means"; // comp.graphics

            IndexSearcher indexSearcher = new IndexSearcher(reader);
            for (String s : queries) {
                // for a certain query

                Collection<String> result = new LinkedList<>();
                try (TokenStream tokenStream = analyzer.tokenStream(TERM_FIELD, s)) {
                    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                    tokenStream.reset();
                    while (tokenStream.incrementToken()) {
                        result.add(charTermAttribute.toString());
                    }
                    tokenStream.end();
                }

                // for each term in the query
                for (String r : result) {

                    TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(TERM_FIELD, r)), 3);

                    // each result is a term with the given text, a class and lat,lon

                    ScoreDoc[] scoreDocs = topDocs.scoreDocs;

                    List<Double> lts = new LinkedList<>();
                    List<Double> lgs = new LinkedList<>();

                    Map<String, Long> wvc = new HashMap<>();
                    for (ScoreDoc scoreDoc : scoreDocs) {
                        Document document = reader.document(scoreDoc.doc);

                        // get term occurrence's word vectors
                        IndexableField[] wvdFields = document.getFields(WV_FIELD);


                        for (IndexableField f : wvdFields) {
                            long v = f.numericValue().longValue();
                            try {
                                double x = GeoPointField.decodeLatitude(v);
                                double y = GeoPointField.decodeLongitude(v);

                                lts.add(x);
                                lgs.add(y);
                            } catch (Exception e) {
                                System.err.println(e.getLocalizedMessage());
                            }
                        }

                        // create wv query

                        // find centroid
                        double qlg = 0d;
                        for (Double lg : lgs) {
                            qlg += lg;
                        }
                        qlg /= lgs.size();

                        double qlt = 0d;
                        for (Double lt : lts) {
                            qlt += lt;
                        }
                        qlt /= lts.size();


                        GeoPointDistanceQuery wvq = new GeoPointDistanceQuery(WV_FIELD, qlt, qlg, 100);
                        TopDocs wvr = indexSearcher.search(wvq, 5);
                        for (ScoreDoc sd : wvr.scoreDocs) {
                            Document d = reader.document(sd.doc);
                            String key = d.getField(CATEGORY_FIELD).stringValue();
                            Long aLong = wvc.get(key);
                            if (aLong == null) {
                                aLong = 0L;
                            }
                            aLong++;
                            wvc.put(key, aLong);
                        }

                    }
                    System.out.println("centroid for " + r + " near to " + wvc);
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

    private void index(String category, List<String> vocabulary, RealMatrix wv, IndexWriter writer, int docid) throws IOException {
        for (int i = 0; i < wv.getColumnDimension(); i++) {
            double[] a = wv.getColumnVector(i).toArray();
            String term = vocabulary.get(i);
            Document document = new Document();
            document.add(new StringField(DOCID_FIELD, new BytesRef(docid), Field.Store.YES));
            document.add(new TextField(TERM_FIELD, term, Field.Store.YES));
            document.add(new TextField(CATEGORY_FIELD, category, Field.Store.YES));
            double latitude = a[0];
            double longitude = a[1];
//            double width = a[2];

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
//            document.add(new Geo3DPoint(WV_FIELD, latitude, longitude));
//            GeoPoint p = new GeoPoint(PlanetModel.WGS84, latitude, longitude);
//            document.add(new Geo3DDocValuesField(WV_FIELD, p));
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
        List<String> strings = IOUtils.readLines(stream);
        for (String line : strings) {
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
