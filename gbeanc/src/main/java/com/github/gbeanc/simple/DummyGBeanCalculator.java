package com.github.gbeanc.simple;

import com.github.gbeanc.GBeanCalculator;
import com.github.gbeanc.prime.DummyPrimeCalculator;
import com.github.gbeanc.prime.GBeanPrimeCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DummyGBeanCalculator implements GBeanCalculator {

  private GBeanPrimeCalculator primeCalculator = new DummyPrimeCalculator();

  public boolean verify(Integer n) {
    boolean isGBC = false;
    if (n > 2 && n % 2 == 0) {
      // get a list of all the prime numbers lower than N
      List<Integer> primes = new ArrayList<Integer>();
      for (int i = 1; i < n; i++) {
        if (primeCalculator.isPrime(i)) {
          primes.add(i);
        }
      }
      // for each of them try to combine it with each of the list to see if their sum == n
      for (Integer i : primes) {
        for (Integer j : primes) {
          if (i + j == n) {
            isGBC = true;
            break;
          }
          //XXX : note that this method finds all the occurrences of a' + a'' = n as it breaks on the 2nd cycle only
        }
      }
    }
    return isGBC;
  }
}
