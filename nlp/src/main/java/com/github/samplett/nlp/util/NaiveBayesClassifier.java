package com.github.samplett.nlp.util;

/**
 * A Naive Bayes Classifier for classifying objects of type I assigning classes of type O
 */
public interface NaiveBayesClassifier<I, O> {

    public O calculateClass(I inputDocument);
}
