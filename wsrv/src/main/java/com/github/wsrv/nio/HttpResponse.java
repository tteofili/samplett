package com.github.wsrv.nio;

import com.github.wsrv.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class HttpResponse {

  private Map<String, String> headers;
  private Integer statusCode;
  private Resource resource;
  private String version;
  private String statusMessage;

  public HttpResponse() {
    this.headers = new HashMap<String, String>();
    this.statusMessage = "";
  }


  public Map<String, String> getHeaders() {
    return headers;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public Resource getResource() {
    return resource;
  }

  public String getVersion() {
    return version;
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(version).append(" ").append(statusCode).append(" ").
            append(statusMessage).append("\n");
    for (String k : headers.keySet()) {
      builder.append(k).append(":").append(" ").append(headers.get(k)).append("\n");
    }
    builder.append("\n");
    builder.append(new String(resource.getBytes()));
    return builder.toString();
  }
}
