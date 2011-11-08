package com.github.wsrv;

import com.github.wsrv.jetty.repository.FSRequestHandlerThread;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class FSRequestHandlerThreadTest {
  @Test
  public void testResourceFetch() {
    try {
      FSRequestHandlerThread fsRequestHandlerThreadTest = new FSRequestHandlerThread("./");
      WSRVResource resource = fsRequestHandlerThreadTest.call();
      assertNotNull(resource);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
