package com.github.wsrv;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * @author tommaso
 */
public class SimpleRequestTest {

  @BeforeClass
  public void setUpBefore() {
    // initialize the server
  }

  @Test
  public void firstCall() {
    try {

    } catch (Exception e) {
      fail(e.getLocalizedMessage());
    }
  }
}
