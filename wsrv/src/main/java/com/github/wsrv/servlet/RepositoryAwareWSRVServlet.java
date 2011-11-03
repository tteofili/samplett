package com.github.wsrv.servlet;

import com.github.wsrv.WSRVResource;
import com.github.wsrv.WSRVResourceManager;
import com.github.wsrv.repository.RepositoryDelegatorRequestHandlerThread;

import javax.servlet.ServletException;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class RepositoryAwareWSRVServlet extends WSRVBaseServlet {

  @Override
  public void init() throws ServletException {
    super.init();
    WSRVResourceManager.getInstance().setResourceRoot(String.valueOf(getInitParameter(ServletParams.ROOT_PARAMETER)));
  }

  @Override
  protected Callable<WSRVResource> getRequestHandlerThread(String resourceName) {
    return new RepositoryDelegatorRequestHandlerThread(resourceName);
  }
}
