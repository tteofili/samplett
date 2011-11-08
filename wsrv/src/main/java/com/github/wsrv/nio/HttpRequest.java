package com.github.wsrv.nio;

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

  @Override
  public String toString() {
    return "HttpRequest{" +
            "path='" + path + '\'' +
            ", headers=" + headers +
            ", method='" + method + '\'' +
            ", version='" + version + '\'' +
            '}';
  }

  public Map<String, String> getHeaders() {
    return headers;
  }
}
