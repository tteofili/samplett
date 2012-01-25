package com.github.samplett.pg.atoi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtoIConverterTest {

  @Test
  public void testConversion() throws Exception {
    DummyAtoIConverter atoIConverter = new DummyAtoIConverter();
    String string = "921311101";
    Integer converted = atoIConverter.convert(string);
    assertEquals(Integer.valueOf(921311101), converted);
    Integer defaultConverted = Integer.parseInt(string);
    assertEquals(defaultConverted, converted);
  }

}
