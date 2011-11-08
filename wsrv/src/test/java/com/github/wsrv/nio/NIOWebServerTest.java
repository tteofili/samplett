package com.github.wsrv.nio;

import org.testng.annotations.Test;

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
  public void testDummyRun() {
    try {
      NIOWebServer server = new NIOWebServer();
      server.init(new ServerConfiguration(10, "./"));
      server.run();
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
