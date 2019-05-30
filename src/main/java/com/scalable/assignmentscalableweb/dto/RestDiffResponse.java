package com.scalable.assignmentscalableweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Rest diff response.
 */
@Data
@NoArgsConstructor
@SuppressWarnings("PMD.ImmutableField")
public class RestDiffResponse implements Serializable {

    @JsonProperty("id")
    private int binaryDataId;

    @JsonProperty("leftSide")
    private String leftSide;

    @JsonProperty("rightSide")
    private String rightSide;

    @JsonProperty("message")
    private String message;

    /**
     * Rest diff response Constructor.
     *
     * @param binaryDataId  binary data id
     * @param leftSide  left base64 data
     * @param rightSide right base64 data
     */
    public RestDiffResponse(final int binaryDataId, final String leftSide, final String rightSide) {
        this.binaryDataId = binaryDataId;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }
}
