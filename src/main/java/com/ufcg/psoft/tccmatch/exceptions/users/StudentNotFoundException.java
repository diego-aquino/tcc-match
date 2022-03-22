package com.ufcg.psoft.tccmatch.exceptions.users;

public class StudentNotFoundException extends UserNotFoundException {

  public StudentNotFoundException() {
    super(message());
  }

  public static String message() {
    return "Student not found.";
  }
}
