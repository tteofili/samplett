package com.github.wsrv.nio.message.response;

import com.github.wsrv.nio.message.request.HttpRequest;

/**
 * An {@link HttpMethodHandler} is responsible for handling a single HTTP method
 *
 * @author tommaso
 */
public interface HttpMethodHandler {
  void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
