package com.github.wsrv.nio;

import java.io.IOException;

/**
 * A generic web server which simply can be initialized, run, stopped
 *
 * @author tommaso
 */
public interface WebServer {

  public void run() throws IOException;

  public void stop() throws IOException;

  public void init() throws InitializationException;
}
