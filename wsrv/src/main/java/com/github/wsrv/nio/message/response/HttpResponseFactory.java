package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.configuration.ServerConfiguration;
import com.github.wsrv.nio.message.request.HttpRequest;
import com.github.wsrv.nio.message.response.method.HttpGETMethodHandler;
import com.github.wsrv.nio.message.response.method.HttpMethodHandler;

/**
 * A class responsible for creating an {@link HttpResponse} from the given {@link HttpResponse}
 *
 * @author tommaso
 */
public class HttpResponseFactory {

  private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

  public HttpResponse createResponse(HttpRequest httpRequest) {
    final HttpResponse httpResponse = new HttpResponse();

    // if no HTTP version is specified in the request, set default to HTTP/1.1
    httpResponse.setVersion(httpRequest.getVersion() != null ? httpRequest.getVersion() : DEFAULT_HTTP_VERSION);
    try {
      // check if method is allowed
      if (!ServerConfiguration.getInstance().getSupportedMethods().contains(httpRequest.getMethod()))
        throw new MethodNotAllowedException(new StringBuilder(httpRequest.getMethod()).append(" method not allowed").toString());

      // only HTTP GET is supported, but other methods could be introduced easily
      if (httpRequest.getMethod().equals("GET")) {
        // get the resource requested in the HTTP request and update the HTTP response
        HttpMethodHandler handler = new HttpGETMethodHandler();
        handler.handle(httpRequest, httpResponse);
      }
    } catch (MethodNotAllowedException e) {
      httpResponse.setStatusCode(405);
    } catch (Exception e) {
      httpResponse.setStatusCode(500);
    } finally {
      httpResponse.setStatusMessage(HTTPStatusCodeNameMapper.map(httpResponse.getStatusCode()));
      if (httpResponse.getStatusCode() >= 400) {
        httpResponse.setResource(new Resource() {
          @Override
          public byte[] getBytes() {
            return new StringBuilder("Server returned:\n").append(httpResponse.getStatusCode())
                    .append(" - ").append(httpResponse.getStatusMessage()).toString().getBytes();
          }
        });
      }
    }
    return httpResponse;
  }


}
