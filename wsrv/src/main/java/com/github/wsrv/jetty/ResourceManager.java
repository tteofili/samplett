package com.github.wsrv.jetty;

import com.github.wsrv.jetty.repository.FileSystemResourceRepository;
import com.github.wsrv.jetty.repository.ResourceRepository;

/**
 * @author tommaso
 */
public class ResourceManager {
  private static ResourceManager ourInstance = new ResourceManager();

  private final ResourceRepository resourceRepository = new FileSystemResourceRepository();

  public static ResourceManager getInstance() {
    return ourInstance;
  }

  private ResourceManager() {
  }

  public void setResourceRoot(String root) {
    this.resourceRepository.initialize(root);
  }

  public ResourceRepository getResourceRepository() {
    return this.resourceRepository;
  }
}
