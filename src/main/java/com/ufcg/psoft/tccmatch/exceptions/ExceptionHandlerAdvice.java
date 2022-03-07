package com.ufcg.psoft.tccmatch.exceptions;

import com.ufcg.psoft.tccmatch.exceptions.api.ApiException;
import com.ufcg.psoft.tccmatch.exceptions.api.ApiExceptionResponseBody;
import com.ufcg.psoft.tccmatch.exceptions.api.BadRequestApiException;
import com.ufcg.psoft.tccmatch.exceptions.api.ConflictApiException;
import com.ufcg.psoft.tccmatch.exceptions.api.ForbiddenApiException;
import com.ufcg.psoft.tccmatch.exceptions.api.NotFoundApiException;
import com.ufcg.psoft.tccmatch.exceptions.api.NotModifiedApiException;
import com.ufcg.psoft.tccmatch.exceptions.api.UnauthorizedApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(BadRequestApiException.class)
  public ResponseEntity<ApiExceptionResponseBody> handle(BadRequestApiException exception) {
    return createResponseEntity(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnauthorizedApiException.class)
  public ResponseEntity<ApiExceptionResponseBody> handle(UnauthorizedApiException exception) {
    return createResponseEntity(exception, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ForbiddenApiException.class)
  public ResponseEntity<ApiExceptionResponseBody> handle(ForbiddenApiException exception) {
    return createResponseEntity(exception, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotFoundApiException.class)
  public ResponseEntity<ApiExceptionResponseBody> handle(NotFoundApiException exception) {
    return createResponseEntity(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotModifiedApiException.class)
  public ResponseEntity<ApiExceptionResponseBody> handle(NotModifiedApiException exception) {
    return createResponseEntity(exception, HttpStatus.NOT_MODIFIED);
  }

  @ExceptionHandler(ConflictApiException.class)
  public ResponseEntity<ApiExceptionResponseBody> handle(ConflictApiException exception) {
    return createResponseEntity(exception, HttpStatus.CONFLICT);
  }

  private ResponseEntity<ApiExceptionResponseBody> createResponseEntity(
    ApiException exception,
    HttpStatus status
  ) {
    return ResponseEntity.status(status).body(new ApiExceptionResponseBody(exception));
  }
}
