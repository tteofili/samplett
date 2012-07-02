package com.github.samplett.nlp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.facet.search.FacetsCollector;
import org.apache.lucene.facet.search.params.CountFacetRequest;
import org.apache.lucene.facet.search.params.FacetSearchParams;
import org.apache.lucene.facet.search.results.FacetResult;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;

/**
 * A Lucene based {@link NaiveBayesClassifier}
 */
public class LuceneSimpleNaiveBayesClassifier implements NaiveBayesClassifier<String, String> {

    //    private Collection<String> vocabulary; // the bag of all the words in the corpus
//    private Map<String, String> docsWithClass; // this is the trained corpus holding a the doc as a key and the class as a value
//    private Map<String, String> classMegaDocMap; // key is the class, value is the megadoc
    //    private Map<String, String> preComputedWordClasses; // the key is the word, the value is its likelihood
    private Map<String, Double> priors;

    private IndexSearcher indexSearcher;
    private String textFieldName;
    private String classFieldName;
    private Map<String, Double> classCounts;
    private int docsWithClassSize;
    private Directory directory;

    public LuceneSimpleNaiveBayesClassifier(Directory directory, String textFieldName, String classFieldName) {

        try {
            this.indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
            this.directory = directory;
            this.textFieldName = textFieldName;
            this.classFieldName = classFieldName;
            createVocabulary();
//        createMegaDocs();
            preComputePriors();
//        preComputeWordClasses();

        } catch (IOException e) {
            e.printStackTrace(); //TODO change this
        }
    }


    private void preComputePriors() throws IOException {
        priors = new HashMap<String, Double>();
        for (String cl : classCounts.keySet()) {
            priors.put(cl, calculatePrior(cl));
        }
    }

//    private void preComputeWordClasses() {
//        Set<String> uniqueWordsVocabulary = new HashSet<String>(vocabulary);
//        for (String d : docsWithClass.keySet()) {
//            calculateClass(d);
//        }
//    }

//    private void createMegaDocs() {
//        classMegaDocMap = new HashMap<String, String>();
//        Map<String, StringBuilder> mockClassMegaDocMap = new HashMap<String, StringBuilder>();
//        for (String doc : docsWithClass.keySet()) {
//            String cl = docsWithClass.get(doc);
//            StringBuilder megaDoc = mockClassMegaDocMap.get(cl);
//            if (megaDoc == null) {
//                megaDoc = new StringBuilder();
//                megaDoc.append(doc);
//                mockClassMegaDocMap.put(cl, megaDoc);
//            } else {
//                mockClassMegaDocMap.put(cl, megaDoc.append(" ").append(doc));
//            }
//        }
//        for (String cl : mockClassMegaDocMap.keySet()) {
//            classMegaDocMap.put(cl, mockClassMegaDocMap.get(cl).toString());
//        }
//    }

    private void createVocabulary() throws IOException {
//        vocabulary = new LinkedList<String>();
//        for (String doc : docsWithClass.keySet()) {
//            String[] split = tokenizeDoc(doc);
//            vocabulary.addAll(Arrays.asList(split));
//        }

        // take the existing classes
        new CountFacetRequest(new CategoryPath("author"), 10);
        FacetSearchParams facetSearchParams = new FacetSearchParams();
        facetSearchParams.addFacetRequest(new CountFacetRequest(new CategoryPath(), 100));
        FacetsCollector facetsCollector = new FacetsCollector(facetSearchParams, indexSearcher.getIndexReader(), new DirectoryTaxonomyReader(directory));
        classCounts = new HashMap<String, Double>();
        indexSearcher.search(new WildcardQuery(new Term(classFieldName, "*")), facetsCollector);
        for (FacetResult facetResult : facetsCollector.getFacetResults()) {
            CategoryPath categoryPath = facetResult.getFacetResultNode().getLabel();
            double count = facetResult.getFacetResultNode().getValue();
            classCounts.put(categoryPath.toString(), count);
        }
        docsWithClassSize = indexSearcher.search(new WildcardQuery(new Term(classFieldName, "*")), 10).totalHits;
    }

    private String[] tokenizeDoc(String doc) {
        // TODO : this is by far not a tokenization, it should be changed
        return doc.split(" ");
    }

    @Override
    public String calculateClass(String inputDocument) throws Exception {
        Double max = 0d;
        String foundClass = null;

        for (String cl : classCounts.keySet()) {
            Double clVal = priors.get(cl) * calculateLikelihood(inputDocument, cl);
            if (clVal > max) {
                max = clVal;
                foundClass = cl;
            }
        }
        return foundClass;
    }


    private Double calculateLikelihood(String document, String c) throws IOException {
        // for each word
        Double result = 1d;
        for (String word : tokenizeDoc(document)) {
            // search with text:word AND class:c
            int hits = countWordInClassC(c, word);

            // num : count the no of times the word appears in documents of class c (+1)
            double num = hits + 1; // +1 is added because of add 1 smoothing

            // den : for the whole dictionary, count the no of times a word appears in documents of class c (+|V|)
            double den = countInClassC(c) + 1;

            // P(w|c) = num/den
            double wordProbability = num / den;
            result *= wordProbability;
        }

        // P(d|c) = P(w1|c)*...*P(wn|c)
        return result;
    }

    private double countInClassC(String c) throws IOException {
//        SlowCompositeReaderWrapper.wrap(indexSearcher.getIndexReader()).fields().terms(textFieldName);

        FacetSearchParams facetSearchParams = new FacetSearchParams();
        facetSearchParams.addFacetRequest(new CountFacetRequest(new CategoryPath(), 100));
        FacetsCollector facetsCollector = new FacetsCollector(facetSearchParams, indexSearcher.getIndexReader(), null);
        indexSearcher.search(new WildcardQuery(new Term(classFieldName, c)), facetsCollector);
        double res = facetsCollector.getFacetResults().size();
        docsWithClassSize = indexSearcher.search(new WildcardQuery(new Term(classFieldName, "*")), 10).totalHits;

        return res;
    }

    private int countWordInClassC(String c, String word) throws IOException {
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(new BooleanClause(new TermQuery(new Term(textFieldName, word)), BooleanClause.Occur.MUST));
        booleanQuery.add(new BooleanClause(new TermQuery(new Term(classFieldName, c)), BooleanClause.Occur.MUST));
        TopDocs topDocs = indexSearcher.search(booleanQuery, 1);
        return topDocs.totalHits;
    }

    private int count(String word, String doc) {
        int count = 0;
        for (String t : tokenizeDoc(doc)) {
            if (t.equals(word))
                count++;
        }
        return count;
    }

    private Double calculatePrior(String currentClass) throws IOException {
        return (double) docCount(currentClass) / docsWithClassSize;
    }

    private int docCount(String countedClass) throws IOException {
        return indexSearcher.search(new TermQuery(new Term(classFieldName, countedClass)), 10).totalHits;
//        for (String c : docsWithClass.values()) {
//            if (c.equals(countedClass)) {
//                count++;
//            }
//        }
    }
}
