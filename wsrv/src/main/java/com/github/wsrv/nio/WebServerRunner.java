package com.github.wsrv.nio;

import com.github.wsrv.nio.configuration.ServerConfiguration;
import com.github.wsrv.nio.configuration.ServerInitializationParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The main class which is used to run the server
 * Usage: java -cp ... com.github.wsrv.nio.WebServerRunner $numOfThreads $repositoryType $repositoryRootNode
 * - $numThreads is the number of threads that should be allocated to the thread pool
 * - $repositoryType is the repository implementation to use
 * - $repositoryRootNode is the root node of the repository used
 *
 * @author tommaso
 */
public class WebServerRunner {
  private final static Logger log = LoggerFactory.getLogger(WebServerRunner.class);

  public static void main(String[] args) {
    WebServer server = new DefaultNIOWebServer();
    try {
      ServerInitializationParameters parameters = parseInitializationParameters(args);
      ServerConfiguration.initialize(parameters.getPoolSize(), parameters.getRepositoryType(),
              parameters.getRepositoryRootNode());
      server.init();
      server.run();
    } catch (Exception e) {
      log.error("Could not start the server due to " + e.getLocalizedMessage());
      try {
        server.stop();
      } catch (IOException e1) {
        // do nothing
      }
      System.exit(-1);
    }
  }

  private static ServerInitializationParameters parseInitializationParameters(String[] args) {
    try {
      Integer poolSize = Integer.valueOf(args[0]);
      if (log.isInfoEnabled())
        log.info("thread number set to: " + poolSize);
      String repoType = args[1];
      if (log.isInfoEnabled())
        log.info("repository type chosen is: " + repoType);
      String root = args[2];
      if (log.isInfoEnabled())
        log.info("root is : " + root);
      assert poolSize != null && repoType != null && root != null;
      return new ServerInitializationParameters(poolSize, repoType, root);
    } catch (Exception e) {
      throw new RuntimeException("missing / wrong input parameters\n usage: java -cp ... com.github.wsrv." +
              "nio.WebServerRunner $numOfThreads $repositoryType $repositoryRootNode");
    }
  }
}
