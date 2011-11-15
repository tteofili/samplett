package com.github.wsrv.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class ResourceRepositoryManager {
  private static ResourceRepositoryManager instance = new ResourceRepositoryManager();

  private final Map<String, ResourceRepository> resourceRepositoryMap = new HashMap<String, ResourceRepository>();

  public static ResourceRepositoryManager getInstance() {
    return instance;
  }

  private ResourceRepositoryManager() {
    resourceRepositoryMap.put("fs", new FileSystemResourceRepository());
  }

  public ResourceRepository getResourceRepository(String repoType) {
    return resourceRepositoryMap.get(repoType);
  }
}
