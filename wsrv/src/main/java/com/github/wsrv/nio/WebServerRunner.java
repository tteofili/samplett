package com.github.wsrv.nio;

/**
 * @author tommaso
 */
public class WebServerRunner {
  public static void main(String[] args) {
    try {
      Integer poolSize = Integer.valueOf(args[0]);
      System.out.println("thread number set to: " + poolSize);
      String root = args[1];
      System.out.println("root is : " + root);
      NIOWebServer server = new NIOWebServer();
      ServerConfiguration serverConfiguration = new ServerConfiguration(poolSize, root);
      server.init(serverConfiguration);
      server.run();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Could not listen on port: 4444");
      System.exit(-1);
    }
  }
}
