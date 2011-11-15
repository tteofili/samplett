package com.github.wsrv.nio.configuration;

/**
 * POJO for server initialization parameters (to be read from the command line)
 *
 * @author tommaso
 */
public class ServerInitializationParameters {

  private Integer poolSize;
  private String repositoryType;
  private String repositoryRootNode;

  public ServerInitializationParameters(Integer poolSize, String repositoryType, String repositoryRootNode) {
    this.poolSize = poolSize;
    this.repositoryType = repositoryType;
    this.repositoryRootNode = repositoryRootNode;
  }

  public Integer getPoolSize() {
    return poolSize;
  }

  public String getRepositoryType() {
    return repositoryType;
  }

  public String getRepositoryRootNode() {
    return repositoryRootNode;
  }

}
