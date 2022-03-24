package com.ufcg.psoft.tccmatch.services;

import com.ufcg.psoft.tccmatch.exceptions.users.students.InvalidPeriodException;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class Validator {

  private static final String PERIOD_PATTERN = "^\\d{4}\\..+$";

  public String validatePeriod(String period) {
    boolean isValidPeriod = matchesPattern(period, PERIOD_PATTERN);
    if (isValidPeriod)
      return period.trim();
    throw new InvalidPeriodException();
  }

  public static boolean matchesPattern(String nullableValue, String regexPattern) {
    if (nullableValue == null)
      return false;
    return Pattern.compile(regexPattern).matcher(nullableValue).matches();
  }
}
