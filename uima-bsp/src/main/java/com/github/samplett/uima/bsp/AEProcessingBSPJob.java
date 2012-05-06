package com.github.samplett.uima.bsp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.messages.BSPMessage;
import org.apache.hama.bsp.messages.ByteMessage;
import org.apache.hama.bsp.sync.SyncException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.ProcessTrace;
import org.apache.uima.util.XMLInputSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Add javadoc here
 */
public class AEProcessingBSPJob<KI, VI, KO, VO, M extends ByteMessage> extends BSP<KI, VI, KO, VO, BSPMessage> {


  private String master;

  @Override
  public void setup(BSPPeer<KI, VI, KO, VO, BSPMessage> peer) throws IOException, SyncException, InterruptedException {
    super.setup(peer);
    master = peer.getAllPeerNames()[0];
  }

  @Override
  public void bsp(BSPPeer<KI, VI, KO, VO, BSPMessage> bspPeer) throws IOException, SyncException, InterruptedException {

    Configuration configuration = bspPeer.getConfiguration();
    String aePath = configuration.get("uima.ae.path");
    try {
      // AE instantiation
      AnalysisEngineDescription analysisEngineDescription = UIMAFramework.getXMLParser().parseAnalysisEngineDescription(new XMLInputSource(aePath));
      AnalysisEngine analysisEngine = UIMAFramework.produceAnalysisEngine(analysisEngineDescription);
      bspPeer.sync();

      // AE initialization
      analysisEngine.initialize(analysisEngineDescription, new HashMap<String, Object>());
      bspPeer.sync();

      // AE execution
      CAS aCAS = analysisEngine.newCAS();
      aCAS.setDocumentText("????");
      ProcessTrace pt = analysisEngine.process(aCAS);
      bspPeer.send(master, new ByteMessage(UUID.randomUUID().toString().getBytes("UTF-8"), pt.toString().getBytes("UTF-8")));
      bspPeer.sync();

      if (isMaster(bspPeer)) {
        ByteMessage bspMessage = (ByteMessage) bspPeer.getCurrentMessage();
        while (bspMessage != null) {
          System.err.println(new String(bspMessage.getData()));
          bspMessage = (ByteMessage) bspPeer.getCurrentMessage();
        }
      }
      analysisEngine.destroy();


    } catch (InvalidXMLException e) {
      e.printStackTrace(); //TODO change this
    } catch (ResourceInitializationException e) {
      e.printStackTrace(); //TODO change this
    } catch (AnalysisEngineProcessException e) {
      e.printStackTrace(); //TODO change this
    }
  }

  private boolean isMaster(BSPPeer<KI, VI, KO, VO, BSPMessage> bspPeer) {
    return bspPeer.getPeerName().equals(master);
  }
}
