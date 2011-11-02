package com.github.wsrv;

/**
 * @author tommaso
 */
public interface WSRVResourceCache<K, V> {
  public V get(K key);

  public V put(K key, V value);
}
