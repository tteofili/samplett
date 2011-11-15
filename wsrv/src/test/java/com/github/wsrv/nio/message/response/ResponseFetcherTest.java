package com.github.wsrv.nio.message.response;

import com.github.wsrv.Resource;
import com.github.wsrv.nio.message.request.HttpRequest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class ResponseFetcherTest {
  @Test
  public void testWorkingFetch() {
    try {
      ResourceFetcher resourceFetcher = new ResourceFetcher();
      HttpRequest httpRequest = mock(HttpRequest.class);
      when(httpRequest.getMethod()).thenReturn("GET");
      when(httpRequest.getPath()).thenReturn("/");
      Resource resource = resourceFetcher.fetchResource(httpRequest);
      assertNotNull(resource);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testFetchWithWrongMethod() {
    try {
      ResourceFetcher resourceFetcher = new ResourceFetcher();
      HttpRequest httpRequest = mock(HttpRequest.class);
      when(httpRequest.getMethod()).thenReturn("GET");
      when(httpRequest.getPath()).thenReturn("/");
      Resource resource = resourceFetcher.fetchResource(httpRequest);
      assertNotNull(resource);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
