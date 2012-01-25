package com.github.samplett.pg.sort;

import java.util.Arrays;

public class BubbleSort {

  public int[] sort(int[] integers) {
    boolean noSwap = false;
    int[] sortedArray = Arrays.copyOf(integers, integers.length);
    while (!noSwap) {
      boolean swap = false;
      for (int i = 0; i < sortedArray.length - 1; i++) {
        if (sortedArray[i] > sortedArray[i + 1]) {
          int s = sortedArray[i + 1];
          int p = sortedArray[i];
          sortedArray[i] = s;
          sortedArray[i + 1] = p;
          swap = true;
        }
      }
      if (!swap)
        noSwap = true;
    }
    return sortedArray;
  }

}
