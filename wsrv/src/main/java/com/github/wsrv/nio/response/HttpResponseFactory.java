package com.github.wsrv.nio.response;

import com.github.wsrv.Resource;
import com.github.wsrv.cache.ResourceCache;
import com.github.wsrv.cache.ResourceCacheProvider;
import com.github.wsrv.nio.request.HttpRequest;
import com.github.wsrv.repository.FSRequestHandlerThread;
import com.github.wsrv.repository.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tommaso
 */
public class HttpResponseFactory {

  private static final Logger log = LoggerFactory.getLogger(HttpResponseFactory.class);

  public static HttpResponse createResponse(HttpRequest httpRequest) {
    HttpResponse httpResponse = new HttpResponse();
    try {
      // check the cache
      ResourceCache<String, Resource> cache = ResourceCacheProvider.getInstance().getCache("in-memory");
      Resource resource = cache.get(httpRequest.getPath());
      if (resource != null) {
        if (log.isDebugEnabled())
          log.debug("hit the cache!");
      } else {
        // get the resource from the repository
        try {
          resource = new FSRequestHandlerThread("." + httpRequest.getPath()).call();
        } catch (ResourceNotFoundException e) {
          // then this should return 404
        }
      }

      // create the response
      httpResponse.setStatusCode(200);
      httpResponse.setVersion(httpRequest.getVersion());
      httpResponse.addHeader("ETag", String.valueOf(httpResponse.hashCode()));
      httpResponse.addHeader("Content-Length", String.valueOf(resource.getBytes().length));
      httpResponse.setResource(resource);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage());
    }
    return httpResponse;
  }
}
