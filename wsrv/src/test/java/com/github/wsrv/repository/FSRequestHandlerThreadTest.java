package com.github.wsrv.repository;

import com.github.wsrv.Resource;
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
      Resource resource = fsRequestHandlerThreadTest.call();
      assertNotNull(resource);
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
