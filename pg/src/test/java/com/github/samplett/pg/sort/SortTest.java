package com.github.samplett.pg.sort;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SortTest {

  @Test
  public void testBubbleSorting() {
    try {
      BubbleSort sorter = new BubbleSort();
      int[] prova = new int[]{3, 2, 5, 6, 2};
      int[] expectedRes = new int[]{2, 2, 3, 5, 6};
      int[] bubbleSorted = sorter.sort(prova);
      for (int i = 0; i < prova.length; i++) {
        assertTrue(expectedRes[i] == bubbleSorted[i]);
      }
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testInsertionSorting() {
    try {
      InsertionSort sorter = new InsertionSort();
      int[] prova = new int[]{3, 2, 5, 6, 2, 1};
      int[] expectedRes = new int[]{1, 2, 2, 3, 5, 6};
      int[] insertionSorted = sorter.sort(prova);
      for (int i = 0; i < prova.length; i++) {
        assertTrue(expectedRes[i] == insertionSorted[i]);
      }
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

  @Test
  public void testBitonicSort() {
    try {
      BitonicSort sorter = new BitonicSort();
      int[] prova = new int[]{3, 2, 6, 1, 4};
      int[] expectedRes = new int[]{1, 2, 3, 6, 4}; // bitonic sequence
      sorter.sort(prova);
      sorter.sort(prova);
      for (int i = 0; i < prova.length; i++) {
        assertTrue(expectedRes[i] == prova[i]);
      }
    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }

}
