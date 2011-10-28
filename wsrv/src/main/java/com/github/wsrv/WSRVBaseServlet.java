package com.github.wsrv;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tommaso
 */
public class WSRVBaseServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
    super.init();
    ThreadExecutorProvider.initialize(Integer.valueOf(getInitParameter("pool-size")));
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // handle HTTP request and params

    // check the cache

    // look for the desired resource

    // eventually get the desired resource

    // return proper HTTP response

  }
}
