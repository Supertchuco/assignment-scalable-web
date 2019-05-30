package com.scalable.assignmentscalableweb.exception;

import com.scalable.assignmentscalableweb.enumerator.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionResourceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(final Exception exception, final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BinaryDataIsBlankException.class)
    public final ResponseEntity<ExceptionResponse> handleBinaryDataIsBlankException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessage.BLANK_BASE64_BINARY_DATA_INPUT.getError(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBase64BinaryDataException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidBase64BinaryDataException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessage.INVALID_BASE64_BINARY_DATA_INPUT.getError(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BinaryDataNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleBinaryDataNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessage.BINARY_DATA_NOT_FOUND.getError(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeftBinaryDataNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleLeftBinaryDataNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessage.LEFT_DATA_NOT_FOUND.getError(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RightBinaryDataNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleRightBinaryDataNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessage.RIGHT_DATA_NOT_FOUND.getError(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedException.class)
    public final ResponseEntity<ExceptionResponse> handleUnexpectedException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessage.UNEXPECTED_ERROR_OCCURRED.getError(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}