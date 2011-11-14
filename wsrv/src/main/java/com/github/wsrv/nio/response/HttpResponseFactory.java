package com.github.wsrv.nio.response;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.request.HttpRequest;

/**
 * @author tommaso
 */
public class HttpResponseFactory {
  public static HttpResponse createResponse(HttpRequest httpRequest, Resource resource) {
    HttpResponse httpResponse = new HttpResponse();
    httpResponse.setStatusCode(200);
    httpResponse.setVersion(httpRequest.getVersion());
    httpResponse.addHeader("ETag", String.valueOf(httpResponse.hashCode()));
    httpResponse.addHeader("Content-Length", String.valueOf(resource.getBytes().length));
    httpResponse.setResource(resource);
    return httpResponse;
  }
}
