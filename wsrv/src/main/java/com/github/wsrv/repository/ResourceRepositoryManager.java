package com.github.wsrv.repository;

import com.github.wsrv.nio.configuration.ServerConfiguration;

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
    FileSystemResourceRepository fileSystemResourceRepository = new FileSystemResourceRepository();
    fileSystemResourceRepository.initialize(ServerConfiguration.getInstance().getRoot());
    resourceRepositoryMap.put("fs", fileSystemResourceRepository);
  }

  public ResourceRepository getResourceRepository(String repoType) {
    return resourceRepositoryMap.get(repoType);
  }
}
