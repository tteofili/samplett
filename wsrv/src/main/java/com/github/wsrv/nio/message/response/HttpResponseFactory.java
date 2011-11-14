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

  public static HttpResponse createResponse(HttpRequest httpRequest) {
    final HttpResponse httpResponse = new HttpResponse();
    httpResponse.setVersion(httpRequest.getVersion());
    try {
      // check the cache
      ResourceCache<String, Resource> cache = StringBasedResourceCacheProvider.getInstance().getCache("in-memory");
      Resource resource = cache.get(httpRequest.getPath());
      if (resource != null) {
        if (log.isDebugEnabled())
          log.debug("hit the cache!");
      } else {
        // get the resource from the repository
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
      }

      // handle connection : keep-alive header
      String connectionHeaderValue = httpRequest.getHeaders().get(Headers.CONNECTION);
      if (connectionHeaderValue != null && connectionHeaderValue.trim().equalsIgnoreCase(Headers.KEEP_ALIVE)) {
        httpResponse.addHeader(Headers.CONNECTION, Headers.KEEP_ALIVE);
      }

      if (httpResponse.getStatusCode() != null) {
        // there was some error retrieving the resource
      } else if (resource != null) {
        if (resource.getBytes() != null && resource.getBytes().length > 0) {
          httpResponse.setStatusCode(200);
          httpResponse.addHeader(Headers.ETAG, String.valueOf(httpResponse.hashCode()));
          httpResponse.addHeader(Headers.CONTENT_LENGTH, String.valueOf(resource.getBytes().length));
          httpResponse.setResource(resource);
        } else {
          httpResponse.setStatusCode(204);
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
}
