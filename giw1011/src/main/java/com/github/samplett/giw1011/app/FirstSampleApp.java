package com.github.samplett.giw1011.app;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;

/**
 * Sample application to run UIMA from another Java class
 */
public class FirstSampleApp {

  public static void runAnalysisEngineDescriptor(String descriptorPath, String text) throws AnalysisEngineProcessException, ResourceInitializationException {
    AnalysisEngine engine = null;
    try {
      // create a UIMA XML resource
      XMLInputSource in = new XMLInputSource(descriptorPath);

      // parse the XML to access the resource defined properties
      ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);

      // create Analysis Engine from the ResourceSpecifier
      engine = UIMAFramework.produceAnalysisEngine(specifier);

    } catch (Exception e) {
      throw new ResourceInitializationException(e);
    }
    try {
      // create a JCas which will contain the extracted annotations
      JCas jcas = engine.newJCas();

      // set the text to be analyzed inside the JCas
      jcas.setDocumentText(text);

      // force document language to "x-unspecified" (usually this is extracted)
      jcas.setDocumentLanguage("x-unspecified");

      // run the analysis engine on the JCas
      engine.process(jcas);

    } catch (Exception e) {
      throw new AnalysisEngineProcessException(e);
    }
  }
}
