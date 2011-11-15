package com.github.wsrv.nio;

/**
 * {@link Exception} thrown when the server cannot be started for some reason
 *
 * @author tommaso
 */
public class InitializationException extends Exception {
  public InitializationException(Throwable cause) {
    super(cause);
  }
}
