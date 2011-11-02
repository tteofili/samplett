package com.github.wsrv;

import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class RepositoryDelegatorRequestHandlerThread implements Callable<WSRVResource> {
  private String resourceName;

  public RepositoryDelegatorRequestHandlerThread(String resourceName) {
    this.resourceName = resourceName;
  }

  public WSRVResource call() throws Exception {
    return ResourceManager.getInstance().getResourceRepository().getResource(resourceName);
  }
}
