package com.github.wsrv.cache;

/**
 * @author tommaso
 */
public interface ResourceCache<K, Resource> {
  public Resource get(K key);

  public Resource put(K key, Resource value);
}
