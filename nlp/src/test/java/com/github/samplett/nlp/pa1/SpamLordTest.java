package com.github.samplett.nlp.pa1;

import org.junit.Test;

import java.util.List;

/**
 * Add javadoc here
 */
public class SpamLordTest {

  @Test
  public void testMain() {
    SpamLord vader = new SpamLord();
    List<SpamLord.Contact> guesses = vader.processDir("src/test/resources/pa1/data/dev");
    List<SpamLord.Contact> gold = vader.loadGold("src/test/resources/pa1/data/devGold");
    vader.score(guesses, gold);
  }
}
