package com.github.wsrv.nio.message.response;

/**
 * {@link Exception} thrown when the HTTP method of the {@link com.github.wsrv.nio.message.request.HttpRequest} is not
 * allowed by the {@link com.github.wsrv.nio.WebServer}
 *
 * @author tommaso
 */
public class MethodNotAllowedException extends Exception {
  public MethodNotAllowedException(String s) {
    super(s);
  }
}
