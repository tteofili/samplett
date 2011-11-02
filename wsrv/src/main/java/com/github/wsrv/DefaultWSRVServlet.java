package com.github.wsrv;

import javax.servlet.ServletException;
import java.util.concurrent.Callable;

/**
 * @author tommaso
 * @version $Id$
 */
public class DefaultWSRVServlet extends WSRVBaseServlet {

  private String baseDir;

  @Override
  public void init() throws ServletException {
    super.init();
    this.baseDir = String.valueOf(getInitParameter(BASE_DIR_PARAMETER));
  }

  @Override
  protected Callable<WSRVResource> getRequestHandlerThread(String resourceName) {
    return new FSRequestHandlerThread(new StringBuilder(baseDir).
            append(resourceName).toString());
  }
}