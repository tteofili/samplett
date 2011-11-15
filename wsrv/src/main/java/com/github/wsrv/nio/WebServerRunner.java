package com.github.wsrv.nio;

import com.github.wsrv.nio.configuration.ServerConfiguration;

import java.io.IOException;

/**
 * @author tommaso
 */
public class WebServerRunner {
  public static void main(String[] args) {
    WebServer server = new DefaultNIOWebServer();
    try {
      // TODO better handle input params as zero, one or more could have been specified on the cmd line
      Integer poolSize = Integer.valueOf(args[0]);
      System.out.println("thread number set to: " + poolSize);
      String root = args[1];
      System.out.println("root is : " + root);
      ServerConfiguration.initialize(poolSize, root);
      server.init();
      server.run();
    } catch (Exception e) {
      System.err.println("Could not start the server due to " + e.getLocalizedMessage());
      try {
        server.stop();
      } catch (IOException e1) {
        // do nothing
      }
      System.exit(-1);
    }
  }
}
