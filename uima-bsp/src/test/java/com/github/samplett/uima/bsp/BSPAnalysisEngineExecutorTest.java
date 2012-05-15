package com.github.samplett.uima.bsp;

import org.junit.Test;

/**
 * Add javadoc here
 */
public class BSPAnalysisEngineExecutorTest {

  @Test
  public void simpleTest() throws Exception {
    BSPAnalysisEngineExecutor bspAnalysisEngineExecutor = new BSPAnalysisEngineExecutor();
    bspAnalysisEngineExecutor.executeAE("src/test/resources/uima/SampleAE.xml", "src/test/resources/data/dev");
  }

}
