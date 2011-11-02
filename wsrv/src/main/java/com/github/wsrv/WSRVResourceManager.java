package com.github.wsrv;

/**
 * @author tommaso
 * @version $Id$
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
