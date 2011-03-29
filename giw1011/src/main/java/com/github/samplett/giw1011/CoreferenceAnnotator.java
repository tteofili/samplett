package com.github.samplett.giw1011;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

/**
 * Discovers coreferences in text
 */
public class CoreferenceAnnotator extends JCasAnnotator_ImplBase{
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
  }
}
