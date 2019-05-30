package com.scalable.assignmentscalableweb.exception;

import lombok.Data;

import java.util.Date;

/**
 * Exception response of assignment scalable web application.
 */
@Data
@SuppressWarnings("PMD.ImmutableField")
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;

    /**
     * Exception response Constructor.
     *
     * @param timestamp token service
     * @param message   error message
     * @param details   error details
     */
    public ExceptionResponse(final Date timestamp, final String message, final String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
