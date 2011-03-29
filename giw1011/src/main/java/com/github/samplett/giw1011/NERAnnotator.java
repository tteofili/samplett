package com.github.samplett.giw1011;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

/**
 * Extracts named entities
 */
public class NERAnnotator extends JCasAnnotator_ImplBase{
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
  }
}
