package com.github.wsrv.repository;

import com.github.wsrv.nio.configuration.ServerConfiguration;

/**
 * A class for retrieving the {@link ResourceRepository} based on the $repositoryType parameter defined during
 * the server initialization
 *
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
    } else
      throw new RuntimeException(new StringBuilder(repoType).append(" type does not exist or has not been registered").toString());
  }

  public ResourceRepository getResourceRepository() {
    return repository;
  }
}
