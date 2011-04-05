package com.github.samplett.giw1011.annotator;

import com.github.samplett.giw1011.ts.NameAnnotation;
import org.apache.commons.io.IOUtils;
import org.apache.uima.TokenAnnotation;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Type;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Annotates first names using a name dictionary and PoSs 
 */
public class NameAnnotator extends JCasAnnotator_ImplBase {

  private static final String NAMESDICT_PATH = "dictpath";
  private static final String POS_FEATURENAME = "posTag";

  private List<String> namesList;

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    try {
      namesList = IOUtils.readLines(new FileInputStream(String.valueOf(aContext
              .getConfigParameterValue(NAMESDICT_PATH))));
      Collections.sort(namesList); // sort collection to make binary search possible
    } catch (IOException e) {
      e.printStackTrace();
      aContext.getLogger().log(Level.SEVERE, "error reading dictionary file");
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    for (Annotation annotation : jcas.getAnnotationIndex(TokenAnnotation.type)) {
      /* create a name annotation for each occurrence of a name in the dictionary */
      if (Collections.binarySearch(namesList, annotation.getCoveredText()) >= 0 && isNP(annotation)) {
        NameAnnotation nameAnnotation = new NameAnnotation(jcas);
        nameAnnotation.setBegin(annotation.getBegin());
        nameAnnotation.setEnd(annotation.getEnd());
        nameAnnotation.addToIndexes();
      }
    }
  }

  private boolean isNP(Annotation annotation) {
    Type type = annotation.getType();
    String pos = annotation.getFeatureValueAsString(type.getFeatureByBaseName(POS_FEATURENAME));
    return pos != null && "np".equals(pos);
  }

}
