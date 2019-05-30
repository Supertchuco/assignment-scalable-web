package com.scalable.assignmentscalableweb.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResponseMessage enum messages.
 */
@AllArgsConstructor
@Getter
public enum ResponseMessage {

    LEFT_SIDE_AND_RIGHT_SIDE_ARE_EQUALS("Binary data left and right are equals."),
    BINARY_DATA_LEFT_RIGHT_NOT_HAVE_SAME_SIZE("Binary data left and right not have same size.");

    private String message;
}