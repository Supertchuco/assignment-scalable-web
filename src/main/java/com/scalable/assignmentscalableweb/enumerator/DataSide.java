package com.scalable.assignmentscalableweb.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum to define the binary data side.
 */
@AllArgsConstructor
@Getter
public enum DataSide {

    LEFT("LEFT"),
    RIGHT("RIGHT");

    private String side;
}
