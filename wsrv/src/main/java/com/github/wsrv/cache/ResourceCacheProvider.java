package com.github.wsrv.cache;

import com.github.wsrv.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class ResourceCacheProvider<T> {

  private static ResourceCacheProvider instance = new ResourceCacheProvider();

  private Map<String, ResourceCache<T, Resource>> cacheMap = new HashMap<String, ResourceCache<T, Resource>>();

  private ResourceCacheProvider() {
    cacheMap.put("in-memory", new InMemoryResourceCache<T, Resource>());
  }

  public static ResourceCacheProvider getInstance() {
    return instance;
  }

  public ResourceCache<T, Resource> getCache(String cacheType) {
    return cacheMap.get(cacheType);
  }
}
