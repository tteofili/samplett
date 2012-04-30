package com.github.samplett.uima.bsp;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CasConsumer;
import org.apache.uima.collection.CollectionProcessingManager;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.StatusCallbackListener;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.ProcessTrace;
import org.apache.uima.util.Progress;

import java.util.Collection;
import java.util.LinkedList;

/**
 * An Apache Hama based {@link CollectionProcessingManager}.
 */
public class BSPCollectionProcessingManager implements CollectionProcessingManager {

  private AnalysisEngine analysisEngine;
  private Collection<StatusCallbackListener> listeners;

  public BSPCollectionProcessingManager() {
    this.listeners = new LinkedList<StatusCallbackListener>();
  }

  @Override
  public AnalysisEngine getAnalysisEngine() {
    return analysisEngine;
  }

  @Override
  public void setAnalysisEngine(AnalysisEngine aAnalysisEngine) throws ResourceConfigurationException {
    analysisEngine = aAnalysisEngine;
  }

  @Override
  public CasConsumer[] getCasConsumers() {
    // TODO : implement this
    return new CasConsumer[0];
  }

  @Override
  public void addCasConsumer(CasConsumer aCasConsumer) throws ResourceConfigurationException {
    // TODO : implement this
  }

  @Override
  public void removeCasConsumer(CasConsumer aCasConsumer) {
    // TODO : implement this
  }

  @Override
  public boolean isSerialProcessingRequired() {
    return false;
  }

  @Override
  public void setSerialProcessingRequired(boolean aRequired) {
    if (aRequired)
      throw new RuntimeException("could not set serial processing for BSP based computations");
  }

  @Override
  public boolean isPauseOnException() {
    return false;
  }

  @Override
  public void setPauseOnException(boolean aPause) {
    // TODO : implement this
    // need Hama fault tolerance capabilities
  }

  @Override
  public void addStatusCallbackListener(StatusCallbackListener aListener) {
    this.listeners.add(aListener);
  }

  @Override
  public void removeStatusCallbackListener(StatusCallbackListener aListener) {
    this.listeners.remove(aListener);
  }

  @Override
  public void process(CollectionReader aCollectionReader) throws ResourceInitializationException {
    // TODO : implement this
  }

  @Override
  public void process(CollectionReader aCollectionReader, int aBatchSize) throws ResourceInitializationException {
    // TODO : implement this
  }

  @Override
  public boolean isProcessing() {
    // TODO : implement this
    return false;
  }

  @Override
  public void pause() {
    // TODO : implement this
  }

  @Override
  public boolean isPaused() {
    // TODO : implement this
    return false;
  }

  @Override
  public void resume(boolean aRetryFailed) {
    // TODO : implement this
  }

  @Override
  public void resume() {
    // TODO : implement this
  }

  @Override
  public void stop() {
    // TODO : implement this
  }

  @Override
  public ProcessTrace getPerformanceReport() {
    return null;
  }

  @Override
  public Progress[] getProgress() {
    return new Progress[0];
  }
}
