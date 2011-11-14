package com.github.wsrv.nio;

import com.github.wsrv.nio.request.HttpRequest;
import com.github.wsrv.nio.request.HttpRequestParser;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author tommaso
 */
public class HttpRequestParserTest {
  @Test
  public void testGET() {
    try {
      String input = "GET /ciao/miao?q=1 HTTP/1.1\n" +
              "Host: localhost:8080\n" +
              "Connection: keep-alive\n" +
              "Cache-Control: no-cache\n" +
              "Pragma: no-cache\n" +
              "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.106 Safari/535.2\n" +
              "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
              "Accept-Encoding: gzip,deflate,sdch\n" +
              "Accept-Language: en-US,en;q=0.8,it;q=0.6\n" +
              "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3\n" +
              "Cookie: ebNewBandWidth_.localhost=68%3A1290785800737; auth=YWRtaW46YWRtaW4=; l10n-submitter=tommaso%20teofili; l10n-license-agreed=true";
      HttpRequestParser httpRequestParser = new HttpRequestParser();
      HttpRequest httpRequest = httpRequestParser.parse(input);
      assertNotNull(httpRequest);
      assertEquals(httpRequest.getMethod(), "GET");
      assertEquals(httpRequest.getPath(), "/ciao/miao?q=1");
      assertEquals(httpRequest.getVersion(), "HTTP/1.1");
      assertNotNull(httpRequest.getHeaders());
      assertTrue(httpRequest.getHeaders().size() == 10);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
