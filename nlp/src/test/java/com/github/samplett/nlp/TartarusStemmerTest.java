package com.github.samplett.nlp;

import org.junit.Test;

/**
 * Add javadoc here
 */
public class TartarusStemmerTest {

  @Test
  public void testBasicExecution() throws Exception {
    TartarusStemmer.main(new String[]{"src/test/resources/tstemmer.txt"});
  }
}
