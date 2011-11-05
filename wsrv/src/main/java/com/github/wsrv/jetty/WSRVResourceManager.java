package com.github.wsrv.jetty;

import com.github.wsrv.jetty.repository.FileSystemResourceRepository;
import com.github.wsrv.jetty.repository.ResourceRepository;

/**
 * @author tommaso
 */
public class WSRVResourceManager {
  private static WSRVResourceManager ourInstance = new WSRVResourceManager();

  private final ResourceRepository resourceRepository = new FileSystemResourceRepository();

  public static WSRVResourceManager getInstance() {
    return ourInstance;
  }

  private WSRVResourceManager() {
  }

  public void setResourceRoot(String root) {
    this.resourceRepository.initialize(root);
  }

  public ResourceRepository getResourceRepository() {
    return this.resourceRepository;
  }
}
