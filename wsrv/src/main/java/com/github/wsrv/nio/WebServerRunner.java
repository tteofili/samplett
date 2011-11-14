package com.github.wsrv.nio;

import java.io.IOException;

/**
 * @author tommaso
 */
public class WebServerRunner {
  public static void main(String[] args) {
    NIOWebServer server = new NIOWebServer();
    try {
      Integer poolSize = Integer.valueOf(args[0]);
      System.out.println("thread number set to: " + poolSize);
      String root = args[1];
      System.out.println("root is : " + root);
      ServerConfiguration serverConfiguration = new ServerConfiguration(poolSize, root);
      server.init(serverConfiguration);
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
