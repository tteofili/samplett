package com.github.samplett.uima.bsp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.message.type.BSPMessage;
import org.apache.hama.bsp.message.type.ByteMessage;
import org.apache.hama.bsp.sync.SyncException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CAS;
import org.apache.uima.util.CasPool;
import org.apache.uima.util.ProcessTrace;
import org.apache.uima.util.XMLInputSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Add javadoc here
 */
public class AEProcessingBSPJob<KI, VI, KO, VO, M extends ByteMessage> extends BSP<KI, VI, KO, VO, BSPMessage> {

  private CasPool casPool;

  private String master;
  private Random r = new Random();

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
      try {
        analysisEngine.initialize(analysisEngineDescription, new HashMap<String, Object>());
      } catch (Exception e) {
        // do nothing
      }
      Integer casPoolSize = Integer.valueOf(bspPeer.getConfiguration().get("cas.pool.size"));
      casPool = new CasPool(casPoolSize, analysisEngine);
//      bspPeer.sync();

      // collection distribution
      if (isMaster(bspPeer)) {
        String dirPath = configuration.get("collection.path");
        int i = 0;
        for (File f : new File(dirPath).listFiles()) {
          FileReader fileReader = new FileReader(f);
          ByteMessage byteMessage = new ByteMessage(UUID.randomUUID().toString().getBytes("UTF-8"), fileReader.toString().getBytes("UTF-8"));
          fileReader.close();
          String toPeer = bspPeer.getAllPeerNames()[r.nextInt(bspPeer.getAllPeerNames().length)];
          bspPeer.send(toPeer, byteMessage);
          i++;
        }
      }
      bspPeer.sync();

      ByteMessage currentMessage;
      while ((currentMessage = (ByteMessage) bspPeer.getCurrentMessage()) != null) {
        // AE execution
        CAS cas = casPool.getCas();
        cas.setDocumentText(new String(currentMessage.getData()));
        ProcessTrace pt = analysisEngine.process(cas);
        casPool.releaseCas(cas);
        bspPeer.send(master, new ByteMessage(UUID.randomUUID().toString().getBytes("UTF-8"), pt.toString().getBytes("UTF-8")));
      }
      bspPeer.sync();

      if (isMaster(bspPeer)) {
        StringBuilder stringBuilder = new StringBuilder();
        ByteMessage bspMessage;
        while ((bspMessage = (ByteMessage) bspPeer.getCurrentMessage()) != null) {
          stringBuilder.append(new String(bspMessage.getData()));
        }
        File f = new File("/Users/teofili/asd.txt");
        f.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        fileOutputStream.write(stringBuilder.toString().getBytes("UTF-8"));
        fileOutputStream.flush();
        fileOutputStream.close();
      }


      try {
        analysisEngine.destroy();
      } catch (Exception e) {
        // do nothing
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isMaster(BSPPeer<KI, VI, KO, VO, BSPMessage> bspPeer) {
    return bspPeer.getPeerName().equals(master);
  }
}
