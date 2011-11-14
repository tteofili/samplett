package com.github.wsrv.nio;

import org.testng.annotations.Test;

import java.util.concurrent.Executors;

import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class NIOWebServerTest {
  @Test
  public void testInitialization() {
    try {
      NIOWebServer server = new NIOWebServer();
      server.init(new ServerConfiguration(10, "./"));
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testStartAndStop() {
    try {
      NIOWebServer server = new NIOWebServer();
      server.init(new ServerConfiguration(10, "./"));
      Executors.newCachedThreadPool().submit(new ServerRunnerThread(server));
      Thread.sleep(2000);
      server.stop();
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  private class ServerRunnerThread implements Runnable {
    private NIOWebServer nioWebServer;

    private ServerRunnerThread(NIOWebServer nioWebServer) {
      this.nioWebServer = nioWebServer;
    }

    @Override
    public void run() {
      try {
        nioWebServer.run();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
