package com.github.samplett.nlp.pa1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Add javadoc here
 */
public class EmailExtractor {

  private static final String EMAIL_REGEX = "(\\w+)(\\(|\\s)?(\\@|at)(\\)|\\s)?((\\w+)((\\.|\\sdot\\s)(\\w+))+)";
  private static final String SCRIPT_EMAIL_REGEX = "(.+obfuscate\\(\\')(.+)(\\'\\,\\')(.+)('\\).+)";

  private Pattern plainEmailPattern = Pattern.compile(EMAIL_REGEX);
  private Pattern scriptEmailPattern = Pattern.compile(SCRIPT_EMAIL_REGEX);

  public String extractEmail(String text) {
    StringBuilder result = new StringBuilder();
    Matcher matcher = scriptEmailPattern.matcher(text);
    if (matcher.find()) {
      result.append(matcher.group(4));
      result.append("@");
      result.append(matcher.group(2));
    } else {
      matcher = plainEmailPattern.matcher(text);
      while (matcher.find()) {
        result.append(matcher.group(1));
        result.append("@");
        result.append(matcher.group(5).replaceAll(" dot ", "."));
      }
    }
    return result.toString();
  }
}
