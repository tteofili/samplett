package com.github.wsrv.nio;

import com.github.wsrv.nio.configuration.ServerConfiguration;
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
      serverDefault.init(new ServerConfiguration(10, "./"));
      serverDefault.stop();
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testStartAndStop() {
    try {
      DefaultNIOWebServer serverDefault = new DefaultNIOWebServer();
      serverDefault.init(new ServerConfiguration(10, "./"));
      Executors.newCachedThreadPool().submit(new ServerRunnerThread(serverDefault));
      Thread.sleep(2000);
      serverDefault.stop();
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
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
