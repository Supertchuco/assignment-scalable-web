package com.scalable.assignmentscalableweb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity represent a binary data object on database.
 */
@Data
@Entity(name = "BinaryData")
@Table(name = "BinaryData")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.ImmutableField")
public class BinaryData implements Serializable {

    @Id
    @Column
    private int dataId;

    @Column
    private String leftData;

    @Column
    private String rightData;

    public BinaryData(final int dataId) {
        this.dataId = dataId;
    }
}
