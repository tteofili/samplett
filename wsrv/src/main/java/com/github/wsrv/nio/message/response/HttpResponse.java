package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.message.Headers;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing an HTTP Response
 *
 * @author tommaso
 */
public class HttpResponse {

  private final Map<String, String> headers;
  private Integer statusCode;
  private Resource resource;
  private String version;
  private String statusMessage;

  public HttpResponse() {
    this.headers = new HashMap<String, String>();
    this.statusMessage = "";
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  Map<String, String> getHeaders() {
    return headers;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  public void addHeader(String name, String value) {
    headers.put(name, value);
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Boolean isKeepAlive() {
    String keepAliveValue = getHeaders().get(Headers.CONNECTION);
    return resource != null && keepAliveValue != null && keepAliveValue.equals(Headers.KEEP_ALIVE);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(version).append(" ").append(statusCode).append(" ").
            append(statusMessage).append("\n");
    for (String k : headers.keySet()) {
      builder.append(k).append(":").append(" ").append(headers.get(k)).append("\n");
    }
    builder.append("\n");
    if (resource != null)
      builder.append(new String(resource.getBytes()));
    return builder.toString();
  }
}
