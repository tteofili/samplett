package com.github.wsrv.nio.message.response;

import com.github.wsrv.nio.message.request.HttpRequest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class HttpResponseFactoryTest {
  @Test
  public void testResponseGenerationWithEmptyRequest() {
    try {
      HttpResponseFactory httpResponseFactory = new HttpResponseFactory();
      HttpRequest httpRequest = mock(HttpRequest.class);
      HttpResponse httpResponse = httpResponseFactory.createResponse(httpRequest);
      assertNotNull(httpResponse);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
