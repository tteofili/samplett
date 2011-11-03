package com.github.wsrv.repository;

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
