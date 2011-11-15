package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.configuration.ServerConfiguration;
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
public class ResourceFetcher {

  public Resource fetchResource(HttpRequest httpRequest, HttpResponse httpResponse) {
    Resource resource = null;
    try {
      ResourceRepository resourceRepository = getRepository();
      resource = resourceRepository.getResource(httpRequest.getPath());
    } catch (ResourceNotFoundException e) {
      httpResponse.setStatusCode(404);
    } catch (NotReadableResourceException e) {
      // the resource cannot be readable for a number of reasons, assuming here it is for lack of permissions
      httpResponse.setStatusCode(403);
    } catch (Exception e) {
      httpResponse.setStatusCode(503);
    }
    return resource;
  }

  private ResourceRepository getRepository() {
    return ResourceRepositoryManager.getInstance().getResourceRepository(
            ServerConfiguration.getInstance().getRepoType());
  }
}
