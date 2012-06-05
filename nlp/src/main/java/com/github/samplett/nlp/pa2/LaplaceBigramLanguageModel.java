package com.github.samplett.nlp.pa2;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LaplaceBigramLanguageModel implements LanguageModel {

    private Map<String, Double> scores = new HashMap<String, Double>();
    private List<String[]> words; // set of words that occur in training

    /**
     * Initialize your data structures in the constructor.
     */
    public LaplaceBigramLanguageModel(HolbrookCorpus corpus) {
        words = new LinkedList<String[]>();
        scores = new HashMap<String, Double>();
        train(corpus);
    }

    /**
     * Takes a corpus and trains your language model.
     * Compute any counts or other corpus statistics in this function.
     */
    public void train(HolbrookCorpus corpus) {
        for (Sentence sentence : corpus.getData()) { // iterate over sentences
            String[] sent = new String[sentence.size()];
            for (int i = 0; i < sentence.size(); i++) { // iterate over words
                String word = sentence.get(i).getWord(); // get the actual word
                sent[i] = word;
            }
            words.add(sent);
        }
        for (Sentence sentence : corpus.getData()) { // iterate over sentences
            for (int i = 0; i < sentence.size() - 1; i++) {
                String precedingWord = sentence.get(i).getWord(); // get the preceding word
                String sequentWord = sentence.get(i + 1).getWord(); // get the sequent word
                String s = new StringBuilder(precedingWord).append(",").append(sequentWord).toString();
                Double aDouble = scores.get(s);
                if (aDouble == null || aDouble <= 0d) {
                    Double prob = calculateLaplaceSmoothingProbability(sequentWord, precedingWord, words, 1d);
                    scores.put(s, prob);
                }
            }
        }

    }

    private Double count(String word, String precedingWord, Collection<String[]> set) {
        Double result = 0d;
        boolean foundPreceding = false;
        for (String[] sentence : set) {
            for (String w : sentence) {
                if (precedingWord.equals(w)) {
                    foundPreceding = true;
                    continue;
                }
                if (foundPreceding && word.equals(w)) {
                    foundPreceding = false;
                    result++;
                } else
                    foundPreceding = false;
            }
        }
        return result;
    }

    private Double count(String word, Collection<String[]> set) {
        Double result = 0d;
        for (String[] sentence : set) {
            for (String w : sentence) {
                if (word.equals(w))
                    result++;
            }
        }
        return result;
    }

    public Double calculateLaplaceSmoothingProbability(String word, String precedingWord, Collection<String[]> set, Double k) {
        return (count(word, precedingWord, set) + k) / (count(precedingWord, set) + k * set.size());
    }

    /**
     * Takes a list of strings as argument and returns the log-probability of the
     * sentence using your language model. Use whatever data you computed in train() here.
     */
    public double score(List<String> sentence) {
        double score = 0.0;
        for (int i = 0; i < sentence.size() - 1; i++) {
            String s = new StringBuilder(sentence.get(i)).append(",").append(sentence.get(i + 1)).toString();
            Double aDouble = scores.get(s);
            if (aDouble != null && aDouble > 0d) {
                score *= Math.log(aDouble);
            } else {
                double v = Math.log(calculateLaplaceSmoothingProbability(sentence.get(i + 1), sentence.get(i), words, 1d));
                score *= v;
                scores.put(s, v);
            }
        }
        return score;
    }

}
