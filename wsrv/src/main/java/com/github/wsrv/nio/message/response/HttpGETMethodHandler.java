package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.cache.ResourceCache;
import com.github.wsrv.cache.StringBasedResourceCacheProvider;
import com.github.wsrv.nio.message.Headers;
import com.github.wsrv.nio.message.request.HttpRequest;
import com.github.wsrv.repository.NotReadableResourceException;
import com.github.wsrv.repository.ResourceNotFoundException;

/**
 * A {@link HttpMethodHandler} for HTTP GET method
 *
 * @author tommaso
 */
public class HttpGETMethodHandler implements HttpMethodHandler {

  public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
    // get the resource requested in the HTTP request and update the HTTP response
    Resource resource = getResource(httpRequest, httpResponse);

    // handle connection : keep-alive header
    if (resource != null)
      handleKeepAliveHeader(httpRequest, httpResponse);

    if (httpResponse.getStatusCode() == null && resource != null) {
      if (resource.getBytes() != null && resource.getBytes().length > 0) {
        markResourceFound(httpResponse, resource);
      } else {
        markEmptyResponse(httpResponse);
      }
    }
  }

  private Resource getResource(HttpRequest httpRequest, HttpResponse httpResponse) {
    // check the cache
    // TODO : it'd be better if the cache was based on client/session instead of request path
    ResourceCache<String, Resource> cache = StringBasedResourceCacheProvider.getInstance().getCache("in-memory");
    Resource resource = cache.get(httpRequest.getPath());
    if (resource == null) {
      // get the resource from the repository
      ResourceFetcher resourceFetcher = new ResourceFetcher();
      try {
        resource = resourceFetcher.fetchResource(httpRequest);
      } catch (ResourceNotFoundException e) {
        httpResponse.setStatusCode(404);
      } catch (NotReadableResourceException e) {
        // the resource cannot be readable for a number of reasons, assuming here it is for lack of permissions
        httpResponse.setStatusCode(403);
      } catch (Exception e) {
        httpResponse.setStatusCode(503);
      }
      if (resource != null)
        cache.put(httpRequest.getPath(), resource);
    }
    return resource;
  }

  private void markResourceFound(HttpResponse httpResponse, Resource resource) {
    httpResponse.setStatusCode(200);
    httpResponse.addHeader(Headers.ETAG, String.valueOf(httpResponse.hashCode()));
    httpResponse.addHeader(Headers.CONTENT_LENGTH, String.valueOf(resource.getBytes().length));
    httpResponse.setResource(resource);
  }

  private void markEmptyResponse(HttpResponse httpResponse) {
    httpResponse.setStatusCode(204);
  }

  private void handleKeepAliveHeader(HttpRequest httpRequest, HttpResponse httpResponse) {
    String connectionHeaderValue = httpRequest.getHeaders().get(Headers.CONNECTION);
    if (connectionHeaderValue != null && connectionHeaderValue.trim().equalsIgnoreCase(Headers.KEEP_ALIVE)) {
      httpResponse.addHeader(Headers.CONNECTION, Headers.KEEP_ALIVE);
    }
  }

}
