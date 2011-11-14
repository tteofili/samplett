package com.github.wsrv.nio.configuration;

/**
 * @author tommaso
 */
public class ServerConfiguration {
  private final String root;
  private final Integer poolSize;

  public ServerConfiguration(Integer poolSize, String root) {
    this.poolSize = poolSize;
    this.root = root;
  }

  public String getRoot() {
    return root;
  }

  public Integer getPoolSize() {
    return poolSize;
  }
}
