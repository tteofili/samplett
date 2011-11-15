package com.github.wsrv.nio.message.response;

/**
 * {@link Exception} thrown when the HTTP method of the {@link com.github.wsrv.nio.message.request.HttpRequest} is not
 * allowed by the {@link com.github.wsrv.nio.WebServer}
 *
 * @author tommaso
 */
class MethodNotAllowedException extends Exception {
  MethodNotAllowedException(String s) {
    super(s);
  }
}
