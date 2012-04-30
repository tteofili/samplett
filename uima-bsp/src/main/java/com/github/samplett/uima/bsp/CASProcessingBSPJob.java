package com.github.samplett.uima.bsp;

import org.apache.hadoop.io.Writable;
import org.apache.hama.bsp.BSP;
import org.apache.hama.bsp.BSPPeer;
import org.apache.hama.bsp.sync.SyncException;

import java.io.IOException;

/**
 * Add javadoc here
 */
public class CASProcessingBSPJob<KI, VI, KO, VO, M extends Writable> extends BSP<KI, VI, KO, VO, M> {


  @Override
  public void bsp(BSPPeer<KI, VI, KO, VO, M> peer) throws IOException, SyncException, InterruptedException {

  }
}
