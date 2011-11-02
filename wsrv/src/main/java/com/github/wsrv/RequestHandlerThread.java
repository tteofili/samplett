package com.github.wsrv;

import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class RequestHandlerThread implements Callable<WSRVResource> {
  private String resourceName;

  public RequestHandlerThread(String resourceName) {
    this.resourceName = resourceName;
  }

  public WSRVResource call() throws Exception {
    return ResourceManager.getInstance().getResourceRepository().getResource(resourceName);
  }
}
