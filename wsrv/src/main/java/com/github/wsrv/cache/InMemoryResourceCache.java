package com.github.wsrv.cache;

import com.github.wsrv.Resource;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Basic in-memory implementation of {@link ResourceCache}
 *
 * @author tommaso
 */
public class InMemoryResourceCache<K> implements ResourceCache<K, Resource> {

  private final Map<K, Resource> cache = new WeakHashMap<K, Resource>();

  @Override
  public Resource get(K key) {
    return cache.get(key);
  }

  @Override
  public Resource put(K key, Resource value) {
    return cache.put(key, value);
  }
}
