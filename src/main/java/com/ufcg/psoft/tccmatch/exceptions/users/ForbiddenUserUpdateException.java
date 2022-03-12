package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.ForbiddenApiException;

public class ForbiddenUserUpdateException extends ForbiddenApiException {

  public ForbiddenUserUpdateException() {
    super(createMessage());
  }

  public static String createMessage() {
    return "No permission to update this user.";
  }
}
