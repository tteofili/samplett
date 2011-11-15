package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.cache.ResourceCache;
import com.github.wsrv.cache.StringBasedResourceCacheProvider;
import com.github.wsrv.nio.message.Headers;
import com.github.wsrv.nio.message.request.HttpRequest;
import com.github.wsrv.repository.FSRequestHandlerThread;
import com.github.wsrv.repository.NotReadableResourceException;
import com.github.wsrv.repository.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tommaso
 */
public class HttpResponseFactory {

  private static final Logger log = LoggerFactory.getLogger(HttpResponseFactory.class);
  private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

  public HttpResponse createResponse(HttpRequest httpRequest) {
    final HttpResponse httpResponse = new HttpResponse();

    // if no HTTP version is specified in the request, set default to HTTP/1.1
    httpResponse.setVersion(httpRequest.getVersion() != null ? httpRequest.getVersion() : DEFAULT_HTTP_VERSION);
    try {
      // check the cache
      // TODO : it'd be better if the cache was based on client/session instead of request path
      ResourceCache<String, Resource> cache = StringBasedResourceCacheProvider.getInstance().getCache("in-memory");
      Resource resource = cache.get(httpRequest.getPath());
      if (resource == null) {
        // get the resource from the repository
        resource = fetchResource(httpRequest, httpResponse);
      }

      // handle connection : keep-alive header
      handleKeepAliveHeader(httpRequest, httpResponse);

      if (httpResponse.getStatusCode() == null && resource != null) {
        if (resource.getBytes() != null && resource.getBytes().length > 0) {
          markResourceFound(httpResponse, resource);
        } else {
          markEmptyResponse(httpResponse);
        }
      }

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

  private Resource fetchResource(HttpRequest httpRequest, HttpResponse httpResponse) {
    Resource resource = null;
    try {
      resource = new FSRequestHandlerThread("." + httpRequest.getPath()).call();
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
}
