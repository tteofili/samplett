package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.cache.ResourceCache;
import com.github.wsrv.cache.StringBasedResourceCacheProvider;
import com.github.wsrv.nio.configuration.ServerConfiguration;
import com.github.wsrv.nio.message.Headers;
import com.github.wsrv.nio.message.request.HttpRequest;
import com.github.wsrv.repository.NotReadableResourceException;
import com.github.wsrv.repository.ResourceNotFoundException;

/**
 * A class responsible for creating an {@link HttpResponse} from the given {@link HttpResponse}
 *
 * @author tommaso
 */
public class HttpResponseFactory {

  private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

  public HttpResponse createResponse(HttpRequest httpRequest) {
    final HttpResponse httpResponse = new HttpResponse();

    // if no HTTP version is specified in the request, set default to HTTP/1.1
    httpResponse.setVersion(httpRequest.getVersion() != null ? httpRequest.getVersion() : DEFAULT_HTTP_VERSION);
    try {
      // check if method is allowed
      if (!ServerConfiguration.getInstance().getSupportedMethods().contains(httpRequest.getMethod()))
        throw new MethodNotAllowedException(new StringBuilder(httpRequest.getMethod()).append(" method not allowed").toString());

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

    } catch (MethodNotAllowedException e) {
      httpResponse.setStatusCode(405);
    } catch (Exception e) {
      httpResponse.setStatusCode(500);
    } finally {
      httpResponse.setStatusMessage(HTTPStatusCodeNameMapper.map(httpResponse.getStatusCode()));
      if (httpResponse.getStatusCode() >= 400) {
        httpResponse.setResource(new Resource() {
          @Override
          public byte[] getBytes() {
            return new StringBuilder("Server returned:\n").append(httpResponse.getStatusCode())
                    .append(" - ").append(httpResponse.getStatusMessage()).toString().getBytes();
          }
        });
      }
    }
    return httpResponse;
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
