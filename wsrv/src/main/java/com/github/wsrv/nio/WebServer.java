package com.github.wsrv.nio;

import java.io.IOException;

/**
 * @author tommaso
 */
public interface WebServer {

  public void run() throws IOException;

  public void stop() throws IOException;
}
