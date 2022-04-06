package com.ufcg.psoft.tccmatch.exceptions.users;

public class ProfessorNotFoundException extends UserNotFoundException {

  public ProfessorNotFoundException() {
    super(message());
  }

  public static String message() {
    return "Professor not found.";
  }
}
