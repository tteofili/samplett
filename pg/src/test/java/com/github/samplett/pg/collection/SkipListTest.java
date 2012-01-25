package com.github.samplett.pg.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SkipListTest {

  @Test
  public void testFirstSkipListImpl() throws Exception {
    SkipList<Integer> skipListIntegers = new FirstSkipList<Integer>();
    long timeConstruction = System.currentTimeMillis();
    fillIntegerList(skipListIntegers);
    timeConstruction = System.currentTimeMillis() - timeConstruction;

    List<Integer> arrayIntegers = new ArrayList<Integer>();
    long timeArConstruction = System.currentTimeMillis();
    fillIntegerList(arrayIntegers);
    timeArConstruction = System.currentTimeMillis() - timeArConstruction;

    int skipListSize = skipListIntegers.size();
    int arrayListSize = arrayIntegers.size();
    assertTrue("different sizes: " + skipListSize + " - " + arrayListSize,
            skipListSize == arrayListSize);

    assertTrue("skipList took longer to build", timeConstruction < timeArConstruction);

  }

  private void fillIntegerList(List<Integer> integers) {
    for (int i = 0; i < 1000000; i++) {
      integers.add(i);
    }
  }

}
