package com.github.tteofili.looseen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.github.tteofili.looseen.yay.SGM;
import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;

/**
 *
 */
public class IndexingWordVectorsTest {

    private static final String PREFIX = "/Users/teofili/data";
    private static final String INDEX = PREFIX + "/20n/index";
    private static final String CATEGORY_FIELD = "category";
    private static final String BODY_FIELD = "body";
    private static final String SUBJECT_FIELD = "subject";

    @Test
    public void test20NExtraction() throws Exception {

        Path mainIndexPath = Paths.get(INDEX + "/original");
        Directory directory = FSDirectory.open(mainIndexPath);

        Path wvPath = Paths.get(INDEX + "/wv");
        delete(wvPath);
        Directory wvIndex = FSDirectory.open(wvPath);

        IndexWriter indexWriter = new IndexWriter(wvIndex, new IndexWriterConfig());
        IndexReader reader = DirectoryReader.open(directory);
        try {
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
                            withDimension(8).
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
                    System.out.println("processed "+ i + " docs");
                }
            }
            indexWriter.commit();

            long indexingEndTime = System.currentTimeMillis();

            System.out.println("word vectors indexed in " + (indexingEndTime - indexingStartTime) / 1000 + " seconds");
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
            document.add(new TextField("term", term, Field.Store.YES));
            document.add(new TextField("category", category, Field.Store.YES));
            document.add(new DoublePoint("wv", a));

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
