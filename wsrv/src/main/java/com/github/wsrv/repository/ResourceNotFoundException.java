package com.github.wsrv.repository;

/**
 * @author tommaso
 */
public class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
