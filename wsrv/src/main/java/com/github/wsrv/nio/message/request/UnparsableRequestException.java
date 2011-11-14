package com.github.wsrv.nio.message.request;

/**
 * @author tommaso
 */
public class UnparsableRequestException extends Exception {
  public UnparsableRequestException(Exception e) {
    super(e);
  }
}
