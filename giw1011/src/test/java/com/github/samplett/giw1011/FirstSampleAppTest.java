package com.github.samplett.giw1011;

import com.github.samplett.giw1011.app.FirstSampleApp;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * TestCase for
 */
public class FirstSampleAppTest {
  @Test
  public void testAppExecution() {
    try {
      FirstSampleApp.runAnalysisEngineDescriptor("src/main/resources/AggregateGiw1011.xml","the big brown fox jumped on the tree");
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getLocalizedMessage());
    }
  }
}
