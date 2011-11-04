package com.github.wsrv.servlet;

import com.github.wsrv.ThreadExecutorProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * @author tommaso
 */
public class WSRVThreadedBaseServlet extends HttpServlet {

  private static String baseDir;

  @Override
  public void init() throws ServletException {
    this.baseDir = String.valueOf(getInitParameter(ServletParams.ROOT_PARAMETER));
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Future<HttpServletResponse> fut = ThreadExecutorProvider.getInstance().getExecutor().submit(new ServletThread(
              baseDir, request, response));
      response = fut.get();
    } catch (Exception e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

}