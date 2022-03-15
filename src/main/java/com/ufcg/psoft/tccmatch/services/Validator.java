package com.ufcg.psoft.tccmatch.services;

import java.util.regex.Pattern;

public abstract class Validator {

  public static boolean matchesPattern(String nullableValue, String regexPattern) {
    if (nullableValue == null) return false;
    return Pattern.compile(regexPattern).matcher(nullableValue).matches();
  }
}
