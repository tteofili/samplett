package com.github.samplett.nlp.util;

import java.util.Collection;

/**
 * utility class for calculating probabilities of bi/uni-grams
 */
public class NGramUtils {

  public static Long calculateProbability(String word, String precedingWord, Collection<String> set) {
    return count(word, precedingWord, set) / count(word, set);
  }

  private static Long count(String word, String precedingWord, Collection<String> set) {
    Long result = 0l;
    boolean foundPreceding = false;
    for (String w : set) {
      if (precedingWord.equals(w)) {
        foundPreceding = true;
      }
      if (foundPreceding && word.equals(w)) {
        foundPreceding = false;
        result++;
      } else
        foundPreceding = false;
    }
    return result;
  }

  private static Long count(String word, Collection<String> set) {
    Long result = 0l;
    for (String w : set) {
      if (word.equals(w))
        result++;
    }
    return result;
  }

  public static Long calculateLaplaceSmoothingProbability(String word, String precedingWord, Collection<String> set, Long k) {
    return (count(word, precedingWord, set) + k) / (count(word, set) + k * set.size());
  }

  public static Long calculateProbabilityUnigramPriorSmoothing(String word, String precedingWord, Collection<String> set, Long k) {
    return (count(word, precedingWord, set) + k * calculateProbability(word, set)) / (count(word, set) + k * set.size());
  }

  private static Long calculateProbability(String word, Collection<String> set) {
    return count(word, set) / set.size();
  }


}
