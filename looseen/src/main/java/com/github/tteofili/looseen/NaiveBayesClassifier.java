package com.github.tteofili.looseen;

/**
 * A Naive Bayes Classifier for classifying objects of type I assigning classes of type O
 */
public interface NaiveBayesClassifier<I, O> {

  public O calculateClass(I inputDocument) throws Exception;
}