package com.github.samplett.giw1011;

import org.apache.uima.UIMAFramework;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.metadata.CpeDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.ProcessTrace;
import org.apache.uima.util.ProcessTraceEvent;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class SimpleCPETest {

  private CollectionProcessingEngine mCPE;

  @Test
  public void testSimpleCPEExec() {
    try {
      runCPE("src/main/resources/SimpleCPE.xml");
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }

  }

  private void runCPE(String descriptorPath) throws InvalidXMLException, IOException,
          ResourceInitializationException, InterruptedException {

    // parse CPE descriptor
    System.out.println("Parsing CPE Descriptor");
    CpeDescription cpeDesc = UIMAFramework.getXMLParser().parseCpeDescription(
            new XMLInputSource(descriptorPath));
    // instantiate CPE
    System.out.println("Instantiating CPE");
    mCPE = UIMAFramework.produceCollectionProcessingEngine(cpeDesc);


    // Start Processing
    System.out.println("Running CPE");
    mCPE.process();

    Thread.sleep(10000);

    ProcessTrace pt = mCPE.getPerformanceReport();
    for (ProcessTraceEvent pte : pt.getEvents()) {
      System.out.println("[" + pte.getType() + "]  " + pte.getComponentName() + " in "
              + pte.getDuration() + "ms");
    }

  }

}
