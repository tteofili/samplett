package com.github.wsrv.nio;

import com.github.wsrv.nio.configuration.ServerConfiguration;

import java.io.IOException;

/**
 * @author tommaso
 */
public class WebServerRunner {
  public static void main(String[] args) {
    DefaultNIOWebServer serverDefault = new DefaultNIOWebServer();
    try {
      Integer poolSize = Integer.valueOf(args[0]);
      System.out.println("thread number set to: " + poolSize);
      String root = args[1];
      System.out.println("root is : " + root);
      ServerConfiguration serverConfiguration = new ServerConfiguration(poolSize, root);
      serverDefault.init(serverConfiguration);
      serverDefault.run();
    } catch (Exception e) {
      System.err.println("Could not start the serverDefault due to " + e.getLocalizedMessage());
      try {
        serverDefault.stop();
      } catch (IOException e1) {
        // do nothing
      }
      System.exit(-1);
    }
  }
}
