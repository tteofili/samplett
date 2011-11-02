package com.github.wsrv;

import javax.servlet.ServletException;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class RepositoryAwareWSRVServlet extends WSRVBaseServlet {

  @Override
  public void init() throws ServletException {
    super.init();
    WSRVResourceManager.getInstance().setResourceRoot(String.valueOf(getInitParameter(BASE_DIR_PARAMETER)));
  }

  @Override
  protected Callable<WSRVResource> getRequestHandlerThread(String resourceName) {
    return new RepositoryDelegatorRequestHandlerThread(resourceName);
  }
}
