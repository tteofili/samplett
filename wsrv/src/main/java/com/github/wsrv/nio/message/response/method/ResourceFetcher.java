package com.github.wsrv.nio.message.response.method;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.message.request.HttpRequest;
import com.github.wsrv.repository.NotReadableResourceException;
import com.github.wsrv.repository.ResourceNotFoundException;
import com.github.wsrv.repository.ResourceRepository;
import com.github.wsrv.repository.ResourceRepositoryManager;

/**
 * A {@link ResourceFetcher} is responsible for fetching a requested resource from a {@link ResourceRepository}
 *
 * @author tommaso
 */
class ResourceFetcher {

  Resource fetchResource(HttpRequest httpRequest) throws ResourceNotFoundException, NotReadableResourceException {
    return getRepository().getResource(httpRequest.getPath());
  }

  private ResourceRepository getRepository() {
    return ResourceRepositoryManager.getInstance().getResourceRepository();
  }
}
