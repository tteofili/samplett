package com.github.wsrv;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tommaso
 */
public class WSRVResourceCacheProvider {

  private static WSRVResourceCacheProvider instance = new WSRVResourceCacheProvider();

  private Map<String, WSRVResourceCache<String, WSRVResource>> cacheMap = new HashMap<String, WSRVResourceCache<String, WSRVResource>>();

  private WSRVResourceCacheProvider() {
    cacheMap.put("in-memory", new InMemoryResourceCache<String, WSRVResource>());
  }

  public static WSRVResourceCacheProvider getInstance() {
    return instance;
  }

  public WSRVResourceCache<String, WSRVResource> getCache(String cacheType) {
    return cacheMap.get(cacheType);
  }
}
