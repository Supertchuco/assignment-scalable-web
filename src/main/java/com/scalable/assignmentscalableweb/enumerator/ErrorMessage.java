package com.scalable.assignmentscalableweb.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorMessage enum messages.
 */
@AllArgsConstructor
@Getter
public enum ErrorMessage {

    UNEXPECTED_ERROR_OCCURRED("Unexpected error ocurred"),
    BINARY_DATA_NOT_FOUND("Base64 Binary data not found on database"),
    INVALID_BASE64_BINARY_DATA_INPUT("Invalid base64 binary data input"),
    BLANK_BASE64_BINARY_DATA_INPUT("Base64 binary data input is blank"),
    LEFT_DATA_NOT_FOUND("Left data not exist"),
    RIGHT_DATA_NOT_FOUND("Right data not exist");

    private String error;
}