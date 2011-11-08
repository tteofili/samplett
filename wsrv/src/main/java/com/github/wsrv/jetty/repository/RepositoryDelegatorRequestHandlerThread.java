package com.github.wsrv.jetty.repository;

import com.github.wsrv.WSRVResource;
import com.github.wsrv.jetty.WSRVResourceManager;

import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class RepositoryDelegatorRequestHandlerThread implements Callable<WSRVResource> {
  private final String resourceName;

  public RepositoryDelegatorRequestHandlerThread(String resourceName) {
    this.resourceName = resourceName;
  }

  public WSRVResource call() throws Exception {
    return WSRVResourceManager.getInstance().getResourceRepository().getResource(resourceName);
  }
}
