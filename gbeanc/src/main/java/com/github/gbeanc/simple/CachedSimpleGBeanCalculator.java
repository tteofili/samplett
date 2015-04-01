package com.github.gbeanc.simple;

import com.github.gbeanc.GBeanCalculator;
import com.github.gbeanc.prime.DummyPrimeCalculator;
import com.github.gbeanc.prime.GBeanPrimeCalculator;

import java.util.ArrayList;
import java.util.Collections;

/**
 */
public class CachedSimpleGBeanCalculator implements GBeanCalculator {

  private GBeanPrimeCalculator primeCalculator = new DummyPrimeCalculator();

  public boolean verify(Integer n) {
    boolean isGBC = false;
    if (n > 2 && n % 2 == 0) {
      /* get a list of all the prime numbers lower than N */

      /* the list is initialized at n/log(n) size is due to the prime numbers theorem */
      int size = (int) (n / Math.log(n));
      ArrayList<Integer> primes = new ArrayList<Integer>(size);

      for (int i = 1; i < n; i++) {
        if (primeCalculator.isPrime(i)) {
          primes.add(i);
        }
      }

      // for each of them try to combine it with each of the list to see if their sum == n
      int i = 0;
      while (i < size - 1 && !isGBC) {
        for (int j = 0; j < size - 1; j++) {
          if (primes.get(i) + primes.get(j) == n) {
            isGBC = true;
            break;
          }
        }
        i++;
      }
    }
    return isGBC;
  }
}
