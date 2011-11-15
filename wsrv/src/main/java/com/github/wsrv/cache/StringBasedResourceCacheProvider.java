package com.github.wsrv.cache;

import com.github.wsrv.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class to retrieve different implementations of {@link ResourceCache} with String as K type
 *
 * @author tommaso
 */
public class StringBasedResourceCacheProvider {

  private static StringBasedResourceCacheProvider instance = new StringBasedResourceCacheProvider();

  private final Map<String, ResourceCache<String, Resource>> cacheMap = new HashMap<String, ResourceCache<String, Resource>>();

  private StringBasedResourceCacheProvider() {
    cacheMap.put("in-memory", new InMemoryResourceCache<String>());
  }

  public static StringBasedResourceCacheProvider getInstance() {
    return instance;
  }

  public ResourceCache<String, Resource> getCache(String cacheType) {
    return cacheMap.get(cacheType);
  }
}
