package com.github.samplett.pg.collection;

import java.util.List;

public class SkipListNode {

  private Object value;

  private List<SkipListNode> forwardPointers;

  private SkipListNode next;

  public SkipListNode getNext() {
    return next;
  }

  public void setNext(SkipListNode next) {
    this.next = next;
  }

  public List<SkipListNode> getForwardPointers() {
    return forwardPointers;
  }

  public void setForwardPointers(List<SkipListNode> forwardPointers) {
    this.forwardPointers = forwardPointers;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
