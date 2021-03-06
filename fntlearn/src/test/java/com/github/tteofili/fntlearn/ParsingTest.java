package com.github.tteofili.fntlearn;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class ParsingTest {
  @Test
  public void metadataExtractionTest() throws Exception {
    Tika tika = new Tika();
    InputStream stream = getClass().getResourceAsStream("/Voti 2013-2014.xlsx");
    try {
      Metadata metadata = new Metadata();
      ContentHandler handler = new DefaultHandler();
      Parser parser = new AutoDetectParser();
      ParseContext context = new ParseContext();

      String mimeType = tika.detect(stream);
      metadata.set(Metadata.CONTENT_TYPE, mimeType);

      parser.parse(stream, handler, metadata, context);
      assertEquals("gianluca", metadata.get("author"));

    } finally {
      stream.close();
    }
  }

  @Test
  public void contentExtractionTest() throws Exception {
    Tika tika = new Tika();
    InputStream stream = getClass().getResourceAsStream("/Voti 2013-2014.xlsx");
    try {
      String text = tika.parseToString(stream);
      assertNotNull(text);
    } finally {
      stream.close();
    }
  }
}
