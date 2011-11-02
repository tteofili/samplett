package com.github.wsrv;

import javax.servlet.ServletException;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 * @version $Id$
 */
public class RepositoryAwareWSRVServlet extends WSRVBaseServlet {

  @Override
  public void init() throws ServletException {
    super.init();
    ResourceManager.getInstance().setResourceRoot(String.valueOf(getInitParameter(BASE_DIR_PARAMETER)));
  }

  @Override
  protected Callable<WSRVResource> getRequestHandlerThread(String resourceName) {
    return new RepositoryDelegatorRequestHandlerThread(resourceName);
  }
}
