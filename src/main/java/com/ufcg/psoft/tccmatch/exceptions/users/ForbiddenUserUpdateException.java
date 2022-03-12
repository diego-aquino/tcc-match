package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.ForbiddenApiException;

public class ForbiddenUserUpdateException extends ForbiddenApiException {

  public ForbiddenUserUpdateException() {
    super(message());
  }

  public static String message() {
    return "No permission to update this user.";
  }
}
