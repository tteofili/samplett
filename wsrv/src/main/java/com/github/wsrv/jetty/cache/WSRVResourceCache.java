package com.github.wsrv.jetty.cache;

/**
 * @author tommaso
 */
public interface WSRVResourceCache<K, WSRVResource> {
  public WSRVResource get(K key);

  public WSRVResource put(K key, WSRVResource value);
}
