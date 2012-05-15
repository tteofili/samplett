package com.github.samplett.uima.bsp;

import org.apache.hadoop.fs.Path;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.BSPJob;

/**
 * Add javadoc here
 */
public class BSPAnalysisEngineExecutor {

  public void executeAE(String aePath, String collectionPath, String outputFilePath) throws Exception {
    HamaConfiguration conf = new HamaConfiguration();
    // set specific job parameters
    conf.set("uima.ae.path", aePath);
    conf.set("output.file", outputFilePath);
    conf.set("cas.pool.size", "3");
    conf.set("collection.path", collectionPath);

    BSPJob job = new BSPJob(conf);
    // set the BSP class which shall be executed
    job.setBspClass(AEProcessingBSPJob.class);
    // help Hama to locale the jar to be distributed
    job.setJarByClass(AEProcessingBSPJob.class);
    // give it a name
    job.setJobName("BSP based UIMA CAS processor");
    // use 4 tasks
    job.setNumBspTask(4);
    job.setOutputPath(new Path("/tmp"));
    // submit the job to the localrunner and wait for its completion, while outputting logs
    job.waitForCompletion(true);
  }

}
