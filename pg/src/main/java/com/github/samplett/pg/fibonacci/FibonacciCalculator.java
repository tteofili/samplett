package com.github.samplett.pg.fibonacci;

public class FibonacciCalculator {

  public Integer calculate(Integer n) {
    assert n > 1 : "Fibonacci length must be at least 2";
    return fib(n);
  }

  private Integer fib(Integer n) {
    if (n == 0)
      return 0;
    if (n == 1)
      return 1;
    return fib(n - 1) + fib(n - 2);
  }

}
