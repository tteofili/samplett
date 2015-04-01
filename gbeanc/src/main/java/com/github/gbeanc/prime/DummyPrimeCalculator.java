package com.github.gbeanc.prime;

/**
 */
public class DummyPrimeCalculator implements GBeanPrimeCalculator {

  public boolean isPrime(Integer n) {
    boolean c = true;
    for (int i = 2; i < n; i++) {
      c = n % i != 0;
      if (c == false)
        break;
    }
    return c;
  }

}
