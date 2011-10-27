package com.github.gbeanc;

/**
 * A {@link com.github.gbeanc.GBeanCalculator} is able to verify if a number n satisfies the Goldbach conjecture
 *
 * @author tommaso
 * @version $Id$
 */
public interface GBeanCalculator {

  public boolean verify(Integer n);

}
