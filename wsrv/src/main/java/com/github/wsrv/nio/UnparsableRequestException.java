package com.github.wsrv.nio;

/**
 * @author tommaso
 */
public class UnparsableRequestException extends Exception {
  public UnparsableRequestException(Exception e) {
    super(e);
  }
}
