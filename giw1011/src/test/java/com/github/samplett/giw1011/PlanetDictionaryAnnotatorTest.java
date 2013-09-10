package com.github.samplett.giw1011;

import com.github.samplett.giw1011.ts.PlanetNameAnnotation;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.test.junit_extension.AnnotatorTester;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * TestCase for DictionaryAnnotator running on unescaped characters
 */
public class PlanetDictionaryAnnotatorTest {

  @Test
  public void dictionaryAnnotatorTest() {
    try {
      CAS cas = AnnotatorTester.performTest("src/main/resources/AggregatePlanetDictionary.xml","ANS is a planet, " +
              "but also A'Hearn is a planet as well. ANSI&Gret seems to be another one. New York is a place, new&york " +
              "is nothing while new&amp;york is wrong",null);
      AnnotationIndex<Annotation> annotationIndex = cas.getJCas().getAnnotationIndex(PlanetNameAnnotation.type);
      assertTrue(annotationIndex.size()>0);
    }
    catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

}
