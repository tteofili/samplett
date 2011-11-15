package com.github.wsrv.repository;

/**
 * {@link Exception} thrown when a {@link com.github.wsrv.Resource} cannot be read for some reason
 *
 * @author tommaso
 */
public class NotReadableResourceException extends Exception {
  public NotReadableResourceException(String s) {
    super(s);
  }
}
