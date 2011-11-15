package com.github.wsrv.jetty.servlet;

import com.github.wsrv.Resource;
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
    // TODO : fix it
//    ResourceRepositoryManager.getInstance().setResourceRoot(String.valueOf(getInitParameter(ServletParams.ROOT_PARAMETER)));
  }

  @Override
  protected Callable<Resource> getRequestHandlerThread(String resourceName) {
    return new RepositoryDelegatorRequestHandlerThread(resourceName);
  }
}
