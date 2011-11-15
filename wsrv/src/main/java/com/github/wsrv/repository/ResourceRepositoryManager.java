package com.github.wsrv.repository;

import com.github.wsrv.nio.configuration.ServerConfiguration;

/**
 * @author tommaso
 */
public class ResourceRepositoryManager {
  private static ResourceRepositoryManager instance = new ResourceRepositoryManager();

  private ResourceRepository repository;

  public static ResourceRepositoryManager getInstance() {
    return instance;
  }

  private ResourceRepositoryManager() {
    String repoType = ServerConfiguration.getInstance().getRepoType();
    String root = ServerConfiguration.getInstance().getRoot();
    if (repoType.equals("fs")) {
      FileSystemResourceRepository fileSystemResourceRepository = new FileSystemResourceRepository();
      fileSystemResourceRepository.initialize(root);
      this.repository = fileSystemResourceRepository;
    } else if (repoType.equals("url")) {
      URLBasedResourceRepository urlBasedResourceRepository = new URLBasedResourceRepository();
      urlBasedResourceRepository.initialize(root);
      this.repository = urlBasedResourceRepository;
    }
  }

  public ResourceRepository getResourceRepository() {
    return repository;
  }
}
