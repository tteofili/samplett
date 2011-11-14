package com.github.wsrv.nio;

/**
 * @author tommaso
 */
public class UnparsableRequest extends Exception {
  public UnparsableRequest(Exception e) {
    super(e);
  }
}
