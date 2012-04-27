package com.github.samplett.nlp.pa1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Add javadoc here
 */
public class PhoneNumberExtractor {
  private static final String PHONE_REGEX = "((Tel|TEL|Phone)\\s?\\:?)\\s*(\\(?(\\+|00)\\d{1,2}\\)?[\\-\\:]\\s*)?((\\(\\d+\\)\\s)?((\\d+\\-?)+))";
  private static final String SCRIPT_PHONE_REGEX = "<a\\shref=\"contact.html\">TEL</a>\\s(\\(?(\\+|00)\\d{1,2}\\)?(\\&.+\\;|\\:)\\s*)?((\\(\\d+\\)\\s)?((\\d+(\\&.+\\;)?)+))";

  private Pattern scriptPhonePattern = Pattern.compile(SCRIPT_PHONE_REGEX);
  private Pattern plainPhoneNumberPattern = Pattern.compile(PHONE_REGEX);

  public String extractPhoneNumber(String phoneNumberText) {
    StringBuilder result = new StringBuilder();
    Matcher matcher = scriptPhonePattern.matcher(phoneNumberText);
    if (matcher.find()) {
      result.append(matcher.group(3).replaceAll("&thinsp;", "-").substring(1));
      result.append(matcher.group(4));
    } else {
      matcher = plainPhoneNumberPattern.matcher(phoneNumberText);
      while (matcher.find()) {
        String group = matcher.group(6);
        if (group != null && group.length() > 0) {
          result.append(matcher.group(6).replace("(", "").replace(") ", ""));
          result.append('-');
          result.append(matcher.group(7));
        } else {
          result.append(matcher.group(5));
        }
      }
    }
    return result.toString();
  }
}
