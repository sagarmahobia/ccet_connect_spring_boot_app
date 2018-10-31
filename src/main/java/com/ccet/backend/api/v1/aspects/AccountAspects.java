package com.ccet.backend.api.v1.aspects;


import com.ccet.backend.api.v1.exceptions.*;
import com.ccet.backend.api.v1.models.commonmodels.Error;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Configuration
public class AccountAspects {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFoundException ex) {
        Error error = new Error();
        error.setErrorCode(ex.getErrorCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<Error> handleInvalidInputException(InvalidInputException ex) {
        Error error = new Error();
        error.setErrorCode(ex.getErrorCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<Error> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        Error error = new Error();
        error.setErrorCode(ex.getErrorCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<Error> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        Error error = new Error();
        error.setErrorCode(ex.getErrorCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Error> handleInternalServerException(InternalServerException ex) {
        Error error = new Error();
        error.setErrorCode(ex.getErrorCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UnknownDatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Error> handleUnknownDatabaseException(UnknownDatabaseException ex) {
        Error error = new Error();
        error.setErrorCode(ex.getErrorCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}

