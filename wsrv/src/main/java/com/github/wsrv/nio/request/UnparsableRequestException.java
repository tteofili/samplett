package com.github.wsrv.nio.request;

/**
 * @author tommaso
 */
public class UnparsableRequestException extends Exception {
  public UnparsableRequestException(Exception e) {
    super(e);
  }
}
