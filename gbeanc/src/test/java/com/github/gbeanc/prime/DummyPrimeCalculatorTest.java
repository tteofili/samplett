package com.github.gbeanc.prime;

import org.junit.Test;

/**
 * @author tommaso
 * @version $Id$
 */
public class DummyPrimeCalculatorTest {

  @Test
  public void primeNessTest() {
    DummyPrimeCalculator dummyPrimeCalculator = new DummyPrimeCalculator();
    assert dummyPrimeCalculator.isPrime(1);
    assert dummyPrimeCalculator.isPrime(3);
    assert dummyPrimeCalculator.isPrime(2);
    assert !dummyPrimeCalculator.isPrime(4);
    assert dummyPrimeCalculator.isPrime(7);
    assert dummyPrimeCalculator.isPrime(23);
    assert !dummyPrimeCalculator.isPrime(27);
  }
}
