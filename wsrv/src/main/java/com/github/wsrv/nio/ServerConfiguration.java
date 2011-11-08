package com.github.wsrv.nio;

/**
 * @author tommaso
 */
public class ServerConfiguration {
  private String root;
  private Integer poolSize;

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
