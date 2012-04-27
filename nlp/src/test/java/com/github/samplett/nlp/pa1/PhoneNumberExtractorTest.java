package com.github.samplett.nlp.pa1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Add javadoc here
 */
public class PhoneNumberExtractorTest {

  private PhoneNumberExtractor phoneNumberExtractor;

  @Before
  public void setUp() {
    this.phoneNumberExtractor = new PhoneNumberExtractor();
  }

  @Test
  public void testExtractFromFormat1() throws Exception {
    String phoneNumberText = "TEL +1-650-723-0293";
    String textExtracted = phoneNumberExtractor.extractPhoneNumber(phoneNumberText);
    assertEquals("650-723-0293", textExtracted);
  }


  @Test
  public void testExtractFromFormat2() throws Exception {
    String phoneNumberText = "Phone:  (650) 723-0293";
    String textExtracted = phoneNumberExtractor.extractPhoneNumber(phoneNumberText);
    assertEquals("650-723-0293", textExtracted);
  }

  @Test
  public void testExtractFromSimpleFormat3() throws Exception {
    String phoneNumberText = "Tel (+1): 650-723-0293";
    String textExtracted = phoneNumberExtractor.extractPhoneNumber(phoneNumberText);
    assertEquals("650-723-0293", textExtracted);
  }

  @Test
  public void testExtractFromScriptFormat() throws Exception {
    String phoneNumberText = "<a href=\"contact.html\">TEL</a> +1&thinsp;650&thinsp;723&thinsp;0293";
    String textExtracted = phoneNumberExtractor.extractPhoneNumber(phoneNumberText);
    assertEquals("650-723-0293", textExtracted);
  }

}
