package com.github.wsrv.nio.message.request;

/**
 * Excception thrown when a String representing an HTTP Request cannot be parsed correctly
 *
 * @author tommaso
 */
class UnparsableRequestException extends Exception {
  public UnparsableRequestException(Exception e) {
    super(e);
  }
}
