package com.github.wsrv;

import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
class RepositoryDelegatorRequestHandlerThread implements Callable<WSRVResource> {
  private final String resourceName;

  RepositoryDelegatorRequestHandlerThread(String resourceName) {
    this.resourceName = resourceName;
  }

  public WSRVResource call() throws Exception {
    return ResourceManager.getInstance().getResourceRepository().getResource(resourceName);
  }
}
