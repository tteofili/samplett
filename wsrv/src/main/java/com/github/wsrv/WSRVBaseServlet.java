package com.github.wsrv;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author tommaso
 */
public abstract class WSRVBaseServlet extends HttpServlet {

  static final String BASE_DIR_PARAMETER = "repo-root";
  private static final String POOL_SIZE_PARAMETER = "pool-size";
  private final Map<String, WSRVResource> cache = new WeakHashMap<String, WSRVResource>();


  @Override
  public void init() throws ServletException {
    ThreadExecutorProvider.initialize(Integer.valueOf(getInitParameter(POOL_SIZE_PARAMETER)));
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // handle HTTP request and params
    System.err.println(request);
    // check the cache
    WSRVResource desiredResource = cache.get(request.getServletPath());
    if (desiredResource != null) {
      System.err.println("hit the cache!");
    } else {
      // look for the desired resource
      ExecutorService executorService = ThreadExecutorProvider.getInstance().getExecutor();
      String resourceName = request.getServletPath() != null ? request.getServletPath() : "";
      System.err.println(new StringBuilder("looking for ").append(resourceName).toString());
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
    System.err.println(response);
  }

  protected abstract Callable<WSRVResource> getRequestHandlerThread(String resourceName);

  private void writeResource(HttpServletResponse response, WSRVResource desiredResource) throws IOException {
    response.getWriter().append(new String(desiredResource.getBytes()));
  }
}
