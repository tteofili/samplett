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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.classification.CachingNaiveBayesClassifier;
import org.apache.lucene.classification.Classifier;
import org.apache.lucene.classification.KNearestNeighborClassifier;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.apache.lucene.classification.utils.ConfusionMatrixGenerator;
import org.apache.lucene.classification.utils.DatasetSplitter;
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
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.DistributionLL;
import org.apache.lucene.search.similarities.DistributionSPL;
import org.apache.lucene.search.similarities.IBSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.LambdaDF;
import org.apache.lucene.search.similarities.LambdaTTF;
import org.apache.lucene.search.similarities.Normalization;
import org.apache.lucene.search.similarities.NormalizationH1;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.LuceneTestCase;
import org.junit.Test;


public final class TestLuceneIndexClassifier extends LuceneTestCase {

    private static final String PREFIX = "/path/to";
    private static final String INDEX = PREFIX + "/itwiki/index";
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String CAT = "cat";

    private static final List<String> CATEGORIES = new LinkedList<>();

    private static final Pattern pattern = Pattern.compile("\\[Categoria\\:(.+)\\|\\s");
    private static final boolean index = false;
    private static final boolean split = true;

    @Test
    public void testItalianWikipedia() throws Exception {

        Path mainIndexPath = Paths.get(INDEX + "/original");
        Directory directory = FSDirectory.open(mainIndexPath);
        Path trainPath = Paths.get(INDEX + "/train");
        Path testPath = Paths.get(INDEX + "/test");
        Path cvPath = Paths.get(INDEX + "/cv");
        FSDirectory cv = null;
        FSDirectory test = null;
        FSDirectory train = null;
        DirectoryReader testReader = null;
        if (split) {
            cv = FSDirectory.open(cvPath);
            test = FSDirectory.open(testPath);
            train = FSDirectory.open(trainPath);
        }

        if (index) {
            delete(mainIndexPath);
            if (split) {
                delete(trainPath, testPath, cvPath);
            }
        }

        IndexReader reader = null;
        try {
            Collection<String> stopWordsList = Arrays.asList("di", "a", "da", "in", "per", "tra", "fra", "il", "lo", "la", "i", "gli", "le");
            CharArraySet stopWords = new CharArraySet(stopWordsList, true);
            Analyzer analyzer = new ItalianAnalyzer(stopWords);
            if (index) {

                System.out.format("Indexing Italian Wikipedia...%n");

                long startIndex = System.currentTimeMillis();
                IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(analyzer));

                importWikipedia(new File(PREFIX + "/itwiki/itwiki-20150405-pages-meta-current1.xml"), indexWriter);
                importWikipedia(new File(PREFIX + "/itwiki/itwiki-20150405-pages-meta-current2.xml"), indexWriter);
                importWikipedia(new File(PREFIX + "/itwiki/itwiki-20150405-pages-meta-current3.xml"), indexWriter);
                importWikipedia(new File(PREFIX + "/itwiki/itwiki-20150405-pages-meta-current4.xml"), indexWriter);


                long endIndex = System.currentTimeMillis();
                System.out.format("Indexed %d pages in %ds %n", indexWriter.maxDoc(), (endIndex - startIndex) / 1000);

                indexWriter.forceMerge(3);

                indexWriter.close();


                System.gc();
            }

            reader = DirectoryReader.open(directory);
            LeafReader originalIndex = SlowCompositeReaderWrapper.wrap(reader);
            LeafReader ar;

            if (index && split) {
                // split the index
                System.out.format("Splitting the index...%n");

                long startSplit = System.currentTimeMillis();
                DatasetSplitter datasetSplitter = new DatasetSplitter(0.003, 0);
                datasetSplitter.split(originalIndex, train, test, cv, analyzer, "title", "text", "cat");
                reader.close();
                reader = DirectoryReader.open(train); // using the train index from now on
                ar = SlowCompositeReaderWrapper.wrap(reader);
                long endSplit = System.currentTimeMillis();
                System.out.format("Splitting done in %ds %n", (endSplit - startSplit) / 1000);
            } else {
                ar = originalIndex;
            }

            // get the categories

            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs topDocs = searcher.search(new WildcardQuery(new Term("cat", "*")), Integer.MAX_VALUE);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                StoredDocument doc = searcher.doc(scoreDoc.doc);
                StorableField cat = doc.getField("cat");
                if (cat != null && !CATEGORIES.contains(cat.stringValue())) {
                    CATEGORIES.add(cat.stringValue());
                }
            }
            System.out.format("Read %d categories...%n", CATEGORIES.size());

            final long startTime = System.currentTimeMillis();

            List<Classifier<BytesRef>> classifiers = new LinkedList<>();
            classifiers.add(new KNearestNeighborClassifier(ar, null, analyzer, null, 1, 0, 0, "cat", "text"));
            classifiers.add(new KNearestNeighborClassifier(ar, null, analyzer, null, 3, 1, 1, "cat", "text"));
            classifiers.add(new KNearestNeighborClassifier(ar, new LMDirichletSimilarity(), analyzer, null, 3, 1, 1, "cat", "text"));
            classifiers.add(new KNearestNeighborClassifier(ar, new LMJelinekMercerSimilarity(0.3f), analyzer, null, 3, 1, 1, "cat", "text"));
            classifiers.add(new KNearestNeighborClassifier(ar, new BM25Similarity(), analyzer, null, 3, 1, 1, "cat", "text"));
            classifiers.add(new KNearestNeighborClassifier(ar, new IBSimilarity(new DistributionSPL(), new LambdaDF(), new Normalization.NoNormalization()), analyzer, null, 3, 1, 1, "cat", "text"));
            classifiers.add(new KNearestNeighborClassifier(ar, new IBSimilarity(new DistributionLL(), new LambdaTTF(), new NormalizationH1()), analyzer, null, 3, 1, 1, "cat", "text"));
            classifiers.add(new CachingNaiveBayesClassifier(ar, analyzer, null, "cat", "text"));
            classifiers.add(new SimpleNaiveBayesClassifier(ar, analyzer, null, "cat", "text"));

            int maxdoc;
            LeafReader testLeafReader;

            if (split) {
                testReader = DirectoryReader.open(test);
                testLeafReader = SlowCompositeReaderWrapper.wrap(testReader);
                maxdoc = testReader.maxDoc();
            } else {
                maxdoc = reader.maxDoc();
            }

            System.out.format("Starting evaluation on %d docs...%n", maxdoc);

            ExecutorService service = Executors.newCachedThreadPool();
            List<Future<String>> futures = new LinkedList<>();
            for (Classifier<BytesRef> classifier : classifiers) {

                futures.add(service.submit(() -> {
                    ConfusionMatrixGenerator.ConfusionMatrix confusionMatrix;
                    if (split) {
                        confusionMatrix = ConfusionMatrixGenerator.getConfusionMatrix(testLeafReader, classifier, "cat", "text");
                    } else {
                        confusionMatrix = ConfusionMatrixGenerator.getConfusionMatrix(ar, classifier, "cat", "text");
                    }

                    final long endTime = System.currentTimeMillis();
                    final int elapse = (int) (endTime - startTime) / 1000;

                    System.out.format("Generated confusion matrix:\n %s \n in %ds %n", confusionMatrix.toString(), elapse);

                    // print results
                    int fc = 0, tc = 0;

                    Map<String, Map<String, Long>> linearizedMatrix = confusionMatrix.getLinearizedMatrix();

                    for (Map.Entry<String, Map<String, Long>> entry : linearizedMatrix.entrySet()) {
                        String correctAnswer = entry.getKey();
                        for (Map.Entry<String, Long> classifiedAnswers : entry.getValue().entrySet()) {
                            Long value = classifiedAnswers.getValue();
                            if (value != null) {
                                if (correctAnswer.equals(classifiedAnswers.getKey())) {
                                    tc += value;
                                } else {
                                    fc += value;
                                }
                            }
                        }

                    }
                    System.err.println("tc:"+tc+",fc:"+fc);
                    float accrate = (float) tc / (float) (tc + fc);
                    float errrate = (float) fc / (float) (tc + fc);
                    return classifier + " -> *** accuracy rate = " + accrate + ", error rate = " + errrate + "; time = " + elapse + " (sec); " + maxdoc + " docs\n";
                }));

            }
            for (Future<String> f : futures) {
                while (!f.isDone()) {
                    Thread.sleep(1000);
                }
                System.out.println(f.get());
            }

            Thread.sleep(10000);
            service.shutdown();

        } finally {
            if (reader != null) {
                reader.close();
            }
            directory.close();
            if (test != null) {
                test.close();
            }
            if (train != null) {
                train.close();
            }
            if (cv != null) {
                cv.close();
            }
            if (testReader != null) {
                testReader.close();
            }
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
        }
        XMLStreamReader reader = factory.createXMLStreamReader(source);
        while (reader.hasNext()) {
            if (count == Integer.MAX_VALUE) {
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
                            page.add(new TextField(TITLE, title, StoredField.Store.YES));
                        }
                        if (text != null) {
                            page.add(new TextField(TEXT, text, StoredField.Store.YES));
                        }
                        for (String cat : cats) {
                            page.add(new StringField(CAT, cat, StoredField.Store.YES));
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