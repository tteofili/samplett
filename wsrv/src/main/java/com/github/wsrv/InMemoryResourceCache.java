package com.github.wsrv;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author tommaso
 */
public class InMemoryResourceCache<K, V> implements WSRVResourceCache<K, V> {

  private Map<K, V> cache = new WeakHashMap<K, V>();

  @Override
  public V get(K key) {
    return cache.get(key);
  }

  @Override
  public V put(K key, V value) {
    return cache.put(key, value);
  }
}
