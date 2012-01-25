package com.github.samplett.pg.sort;

import java.util.Arrays;

public class InsertionSort {

  public int[] sort(int[] integers) {
    int[] sortedArray = new int[integers.length];
    for (int k = 0; k < sortedArray.length; k++) {
      sortedArray[k] = Integer.MAX_VALUE;
    }
    for (int i = 0; i < integers.length; i++) {
      int toInsert = integers[i];
      for (int j = 0; j < sortedArray.length; j++) {
        if (toInsert < sortedArray[j]) {
          sortedArray = insertAtPosition(toInsert, j, sortedArray);
          break;
        }
      }
    }
    return sortedArray;
  }

  private int[] insertAtPosition(int digit, int position, int[] array) {
    int[] sortedArray = Arrays.copyOf(array, array.length);
    for (int i = position; i < array.length - 1; i++) {
      sortedArray[i + 1] = array[i];
    }
    sortedArray[position] = digit;
    return sortedArray;
  }

}
