package com.github.wsrv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author tommaso
 */
class ServletThread implements Callable<HttpServletResponse> {
  private transient HttpServletRequest request;
  private transient HttpServletResponse response;
  private transient String baseDir;
  private final Logger log = LoggerFactory.getLogger(ServletThread.class);


  public ServletThread(String baseDir, HttpServletRequest request, HttpServletResponse response) {
    this.baseDir = baseDir;
    this.request = request;
    this.response = response;
  }

  @Override
  public HttpServletResponse call() throws Exception {
    // handle HTTP request and params
    if (log.isDebugEnabled())
      log.debug(request.toString());
    // check the cache
    WSRVResourceCache<String, WSRVResource> cache = WSRVResourceCacheProvider.getInstance().getCache("in-memory");
    WSRVResource desiredResource = cache.get(request.getServletPath());
    if (desiredResource != null) {
      if (log.isDebugEnabled())
        log.debug("hit the cache!");
    } else {
      // look for the desired resource
      ExecutorService executorService = ThreadExecutorProvider.getInstance().getExecutor();
      String resourceName = request.getServletPath() != null ? request.getServletPath() : "";
      if (log.isDebugEnabled())
        log.debug(new StringBuilder("looking for ").append(resourceName).toString());
      Future<WSRVResource> fut = executorService.submit(new FSRequestHandlerThread(new StringBuilder(baseDir).
              append(resourceName).toString()));
      try {
        // eventually get the desired resource
        desiredResource = fut.get();
        // update the cache
        cache.put(request.getQueryString(), desiredResource);
      } catch (Exception e) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
      }
    }

    if (desiredResource != null) {
      response.getWriter().println(new String(desiredResource.getBytes()));
    }

    // return proper HTTP response
    if (log.isDebugEnabled())
      log.debug(response.toString());
    return response;
  }
}
