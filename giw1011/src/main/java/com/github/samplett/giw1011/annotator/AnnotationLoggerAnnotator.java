package com.github.samplett.giw1011.annotator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.Level;

/**
 * {@link org.apache.uima.analysis_component.JCasAnnotator_ImplBase} extension which simply logs annotations
 */
public class AnnotationLoggerAnnotator extends JCasAnnotator_ImplBase{
  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    for (Annotation annotation :jcas.getAnnotationIndex(Annotation.type)) {
      getContext().getLogger().log(Level.INFO,new StringBuilder(annotation.toString()).append("   coveredText: ").
              append(annotation.getCoveredText()).toString());
    }
  }
}
