package com.github.wsrv.cache;

import com.github.wsrv.jetty.WSRVResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class WSRVResourceCacheProvider<T> {

  private static WSRVResourceCacheProvider instance = new WSRVResourceCacheProvider();

  private Map<String, WSRVResourceCache<T, WSRVResource>> cacheMap = new HashMap<String, WSRVResourceCache<T, WSRVResource>>();

  private WSRVResourceCacheProvider() {
    cacheMap.put("in-memory", new InMemoryResourceCache<T, WSRVResource>());
  }

  public static WSRVResourceCacheProvider getInstance() {
    return instance;
  }

  public WSRVResourceCache<T, WSRVResource> getCache(String cacheType) {
    return cacheMap.get(cacheType);
  }
}
