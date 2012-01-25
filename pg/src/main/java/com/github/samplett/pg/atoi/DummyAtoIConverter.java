package com.github.samplett.pg.atoi;

public class DummyAtoIConverter {

  /**
   * suppose you can't parse an entire string but only a char at a time
   *
   * @param string
   * @return Integer
   */
  public Integer convert(String string) {
    Integer converted = Integer.valueOf(0);
    for (int i = 0; i < string.length(); i++) {
      int c = Integer.parseInt(String.valueOf(string.charAt(string
              .length()
              - 1 - i)));
      converted += c * (int) (Math.pow(10d, i));
    }
    return converted;
  }

}
