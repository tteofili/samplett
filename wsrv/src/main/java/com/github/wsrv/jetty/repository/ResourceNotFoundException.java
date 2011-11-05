package com.github.wsrv.jetty.repository;

/**
 * @author tommaso
 */
class ResourceNotFoundException extends Exception {
  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }

  ResourceNotFoundException(String message) {
    super(message);
  }
}
