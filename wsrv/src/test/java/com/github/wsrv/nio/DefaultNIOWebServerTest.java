package com.github.wsrv.nio;

import org.testng.annotations.Test;

import java.util.concurrent.Executors;

import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class DefaultNIOWebServerTest {
  @Test
  public void testInitialization() {
    try {
      DefaultNIOWebServer serverDefault = new DefaultNIOWebServer();
      serverDefault.init();
      serverDefault.stop();
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testStartAndStop() {
    try {
      DefaultNIOWebServer serverDefault = new DefaultNIOWebServer();
      serverDefault.init();
      Executors.newCachedThreadPool().submit(new ServerRunnerThread(serverDefault));
      Thread.sleep(2000);
      serverDefault.stop();
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testRunWithMissingInitialization() {
    DefaultNIOWebServer serverDefault = new DefaultNIOWebServer();
    try {
      serverDefault.run();
      fail("should not run if server is not initialized");
    } catch (Exception e) {
      // everything ok
    }
  }

  @Test
  public void testStopWithMissingInitialization() {
    DefaultNIOWebServer serverDefault = new DefaultNIOWebServer();
    try {
      serverDefault.stop();
      fail("should not stop if server is not initialized");
    } catch (Exception e) {
      // everything ok
    }
  }

  private class ServerRunnerThread implements Runnable {
    private DefaultNIOWebServer defaultNioWebServer;

    private ServerRunnerThread(DefaultNIOWebServer defaultNioWebServer) {
      this.defaultNioWebServer = defaultNioWebServer;
    }

    @Override
    public void run() {
      try {
        defaultNioWebServer.run();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
