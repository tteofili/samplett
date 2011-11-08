package com.github.wsrv.nio;

/**
 * @author tommaso
 */
public class WebServerRunner {
  public static void main(String[] args) {
    try {
      Integer poolSize = Integer.valueOf(args[1]);
      String root = args[2];
      NIOWebServer server = new NIOWebServer();
      ServerConfiguration serverConfiguration = new ServerConfiguration(poolSize, root);
      server.init(serverConfiguration);
      server.run();
    } catch (Exception e) {
      System.err.println("Could not listen on port: 4444");
      System.exit(-1);
    }
  }
}
