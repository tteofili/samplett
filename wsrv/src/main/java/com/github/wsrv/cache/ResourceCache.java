package com.github.wsrv.cache;

/**
 * A {@link ResourceCache} is a cache with K as key type and {@link} Resource as value type
 *
 * @author tommaso
 */
public interface ResourceCache<K, Resource> {
  public Resource get(K key);

  public Resource put(K key, Resource value);
}
