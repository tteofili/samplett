package com.github.samplett.nlp.pa2;

import java.util.*;

public class LaplaceUnigramLanguageModel implements LanguageModel {

  protected List<String> words; // set of words that occur in training
  private Map<String, Double> scores;

  /**
   * Initialize your data structures in the constructor.
   */
  public LaplaceUnigramLanguageModel(HolbrookCorpus corpus) {
    words = new LinkedList<String>();
    scores = new HashMap<String, Double>();
    train(corpus);
  }

  /**
   * Takes a corpus and trains your language model.
   * Compute any counts or other corpus statistics in this function.
   */
  public void train(HolbrookCorpus corpus) {
    for (Sentence sentence : corpus.getData()) { // iterate over sentences
      for (Datum datum : sentence) { // iterate over words
        String word = datum.getWord(); // get the actual word
        words.add(word);
      }
    }
    for (String word : words) { // iterate over words
      scores.put(word, calculateProbability(word, words));
    }

  }

//  private double calculateUnigramLaplaceProbability(String word) {
//    return (1 + calculateProbability(word, words)) / (count(word, words) + words.size());
//  }

  /**
   * Takes a list of strings as argument and returns the log-probability of the
   * sentence using your language model. Use whatever data you computed in train() here.
   */
  public double score(List<String> sentence) {
    double score = 0.0;
    for (String word : sentence) { // iterate over words in the sentence
      Double aDouble = scores.get(word);
      if (aDouble != null)
        score *= Math.log(aDouble + 1 / words.size());
      else
        score *= Math.log(1 / words.size());
    }
    return score;
  }

  private Double calculateProbability(String word, Collection<String> set) {
    return count(word, set) / set.size();
  }

  private Double count(String word, Collection<String> set) {
    Double result = 0d;
    for (String w : set) {
      if (word.equals(w))
        result++;
    }
    return result;
  }

}
