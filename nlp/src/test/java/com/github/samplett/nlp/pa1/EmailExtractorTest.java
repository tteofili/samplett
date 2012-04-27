package com.github.samplett.nlp.pa1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Add javadoc here
 */
public class EmailExtractorTest {

  private EmailExtractor emailExtractor;

  @Before
  public void setUp() {
    this.emailExtractor = new EmailExtractor();
  }

  @Test
  public void extractFromEmailText() throws Exception {
    String emailText = "jurafsky@stanford.edu";
    String textExtracted = emailExtractor.extractEmail(emailText);
    assertEquals("jurafsky@stanford.edu", textExtracted);
  }


  @Test
  public void extractFromEmailWithATText() throws Exception {
    String emailText = "jurafsky(at)cs.stanford.edu";
    String textExtracted = emailExtractor.extractEmail(emailText);
    assertEquals("jurafsky@cs.stanford.edu", textExtracted);
  }

  @Test
  public void extractFromEmailWithSpacesText() throws Exception {
    String emailText = "jurafsky at csli dot stanford dot edu";
    String textExtracted = emailExtractor.extractEmail(emailText);
    assertEquals("jurafsky@csli.stanford.edu", textExtracted);
  }

  @Test
  public void extractFromScriptedEmailText() throws Exception {
    String emailText = "<script type=\"text/javascript\">obfuscate('stanford.edu','jurafsky')</script>";
    String textExtracted = emailExtractor.extractEmail(emailText);
    assertEquals("jurafsky@stanford.edu", textExtracted);
  }

}
