package com.scalable.assignmentscalableweb.repository;

import com.scalable.assignmentscalableweb.entities.BinaryData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository  data repository to find and persist binary data objects on database.
 */
@Repository
public interface BinaryDataRepository extends CrudRepository<BinaryData, Integer> {

    /**
     * Find binary data object on database using data id.
     */
    BinaryData findByDataId(int dataId);
}
