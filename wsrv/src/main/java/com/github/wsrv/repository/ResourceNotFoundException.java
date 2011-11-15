package com.github.wsrv.repository;

/**
 * {@link Exception} thrown when a {@link com.github.wsrv.Resource} cannot be found
 *
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
