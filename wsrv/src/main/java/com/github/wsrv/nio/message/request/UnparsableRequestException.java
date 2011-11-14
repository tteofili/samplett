package com.github.wsrv.nio.message.request;

/**
 * @author tommaso
 */
class UnparsableRequestException extends Exception {
  public UnparsableRequestException(Exception e) {
    super(e);
  }
}
