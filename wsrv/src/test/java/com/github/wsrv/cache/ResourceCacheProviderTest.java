package com.github.wsrv.cache;

import com.github.wsrv.Resource;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author tommaso
 */
public class ResourceCacheProviderTest {
  @Test
  public void testCacheTyping() {
    try {
      ResourceCache<Object, Resource> genericCache = ResourceCacheProvider.getInstance().getCache("in-memory");
      ResourceCache<String, Resource> stringBasedCache = ResourceCacheProvider.getInstance().getCache("in-memory");
      assertNotNull(genericCache);
      assertNotNull(stringBasedCache);
      assertTrue(genericCache.equals(stringBasedCache));
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
