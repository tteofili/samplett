package com.github.wsrv.jetty.servlet;

import com.github.wsrv.WSRVResource;
import com.github.wsrv.jetty.repository.FSRequestHandlerThread;

import javax.servlet.ServletException;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 */
public class DefaultWSRVServlet extends WSRVBaseServlet {

  private String baseDir;

  @Override
  public void init() throws ServletException {
    super.init();
    this.baseDir = String.valueOf(getInitParameter(ServletParams.ROOT_PARAMETER));
  }

  @Override
  protected Callable<WSRVResource> getRequestHandlerThread(String resourceName) {
    return new FSRequestHandlerThread(new StringBuilder(baseDir).
            append(resourceName).toString());
  }
}
