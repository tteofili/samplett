package com.github.gbeanc.simple;

import com.github.gbeanc.GBeanCalculator;
import org.junit.Test;

/**
 * Tests for {@link com.github.gbeanc.simple.SimpleGBeanCalculator}
 */
public class SimpleGBeanCalculatorTest {

  @Test
  public void testPositive() {
    GBeanCalculator calculator = new SimpleGBeanCalculator();
    assert calculator.verify(4);
    assert calculator.verify(6);
    assert calculator.verify(8);
    assert calculator.verify(10);
    assert calculator.verify(12);
    assert calculator.verify(14);
  }

  @Test
  public void testNegative() {
    GBeanCalculator calculator = new SimpleGBeanCalculator();
    assert !calculator.verify(1);
    assert !calculator.verify(2);
    assert !calculator.verify(3);
    assert !calculator.verify(5);
    assert !calculator.verify(7);
    assert !calculator.verify(9);
  }

  @Test
  public void testBigPositive() {
    GBeanCalculator calculator = new SimpleGBeanCalculator();
    long start = System.currentTimeMillis();
    assert calculator.verify(21312);
    assert !calculator.verify(25153);
    assert calculator.verify(65474);
    assert calculator.verify(12412);
    assert calculator.verify(23132);
    assert !calculator.verify(46335);
    assert calculator.verify(463532);
    long end = System.currentTimeMillis();
    System.err.println("Time elapsed : " + (end - start));
  }
}
