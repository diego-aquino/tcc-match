package com.ufcg.psoft.tccmatch.exceptions.users;

import com.ufcg.psoft.tccmatch.exceptions.api.ForbiddenApiException;
import com.ufcg.psoft.tccmatch.models.users.User;

public class ForbiddenUserTypeException extends ForbiddenApiException {

  public ForbiddenUserTypeException(User.Type forbiddenUserType) {
    super(message(forbiddenUserType));
  }

  public static String message(User.Type forbiddenUserType) {
    return String.format(
      "Users of type '%s' are not allowed to use this resource.",
      forbiddenUserType.toString()
    );
  }
}
