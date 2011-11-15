package com.github.wsrv.nio;

/**
 * {@link Exception} thrown when the server cannot be started for some reason
 *
 * @author tommaso
 */
class InitializationException extends Exception {
  InitializationException(Throwable cause) {
    super(cause);
  }
}
