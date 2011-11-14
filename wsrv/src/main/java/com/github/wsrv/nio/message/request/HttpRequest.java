package com.github.wsrv.nio.message.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class HttpRequest {
  private String path;
  private Map<String, String> headers;
  private String method;
  private String version;

  public HttpRequest() {
    headers = new HashMap<String, String>();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void addHeader(String name, String value) {
    headers.put(name, value);
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getMethod() {
    return this.method;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getVersion() {
    return this.version;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(method).append(" ").append(path).append(" ").append(version).append("\n");
    for (String k : headers.keySet()) {
      builder.append(k).append(":").append(" ").append(headers.get(k)).append("\n");
    }
    return builder.toString();
  }

}
