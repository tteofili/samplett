package com.github.samplett.pg.fibonacci;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FibonacciCalculatorTest {

  @Test
  public void testFib1() {
    FibonacciCalculator calculator = new FibonacciCalculator();
    assertTrue(calculator.calculate(2) == 1);
  }

  @Test
  public void testFib3() {
    FibonacciCalculator calculator = new FibonacciCalculator();
    Integer fib3 = calculator.calculate(3);
    assertTrue(fib3 == 2);
  }

  @Test
  public void testFib4() {
    FibonacciCalculator calculator = new FibonacciCalculator();
    Integer fib4 = calculator.calculate(4);
    assertTrue(fib4 == 3);
  }

  @Test
  public void testFib5() {
    FibonacciCalculator calculator = new FibonacciCalculator();
    Integer fib5 = calculator.calculate(5);
    assertTrue(fib5 == 5);
  }

  @Test
  public void testFib10() {
    FibonacciCalculator calculator = new FibonacciCalculator();
    Integer fib10 = calculator.calculate(10);
    assertTrue(fib10 == 55);
  }
}
