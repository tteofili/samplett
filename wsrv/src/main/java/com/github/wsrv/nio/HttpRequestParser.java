package com.github.wsrv.nio;

/**
 * @author tommaso
 */
public class HttpRequestParser {

  public HttpRequest parse(String requestString) {
    int endFirstLine = requestString.indexOf("\n");
    String initialLine = requestString.substring(0, endFirstLine);
    System.err.println(initialLine);
    final HttpRequest httpRequest = new HttpRequest();
    String[] initalLineTokens = initialLine.split("\\s");
    System.err.println(initalLineTokens[0] + " " + initalLineTokens[1] + " " + initalLineTokens[2]);
    httpRequest.setMethod(initalLineTokens[0]);
    httpRequest.setPath(initalLineTokens[1]);
    httpRequest.setVersion(initalLineTokens[2]);
    String[] headers = requestString.substring(endFirstLine + 1).split("\n");
    for (String h : headers) {
      try {
        String[] hmap = h.split(":");
        httpRequest.addHeader(hmap[0], hmap[1]);
      } catch (Exception e) {
        // do nothing
      }
    }
    return httpRequest;
  }
}
