package com.github.wsrv;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author tommaso
 */
public abstract class WSRVBaseServlet extends HttpServlet {

  private final Logger log = LoggerFactory.getLogger(WSRVBaseServlet.class);

  @Override
  public void init() throws ServletException {
    ThreadExecutorProvider.initialize(Integer.valueOf(getInitParameter(ServletParams.POOL_SIZE_PARAMETER)));
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
      Future<WSRVResource> fut = executorService.submit(getRequestHandlerThread(resourceName));
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
      writeResource(response, desiredResource);
    }

    // return proper HTTP response
    if (log.isDebugEnabled())
      log.debug(response.toString());
  }

  protected abstract Callable<WSRVResource> getRequestHandlerThread(String resourceName);

  private void writeResource(HttpServletResponse response, WSRVResource desiredResource) throws IOException {
    response.getWriter().append(new String(desiredResource.getBytes()));
  }
}
