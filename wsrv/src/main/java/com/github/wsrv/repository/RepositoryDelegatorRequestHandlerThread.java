package com.github.wsrv.repository;

import com.github.wsrv.Resource;
import com.github.wsrv.jetty.ResourceManager;

import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class RepositoryDelegatorRequestHandlerThread implements Callable<Resource> {
  private final String resourceName;

  public RepositoryDelegatorRequestHandlerThread(String resourceName) {
    this.resourceName = resourceName;
  }

  public Resource call() throws Exception {
    return ResourceManager.getInstance().getResourceRepository().getResource(resourceName);
  }
}
