package com.github.wsrv;

/**
 * A generic object the client is interested in which holds its contents inside an array of {@link Byte}s
 *
 * @author tommaso
 */
public interface Resource {
  Byte[] getBytes();
}
