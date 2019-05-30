package com.scalable.assignmentscalableweb.service;

import com.scalable.assignmentscalableweb.dto.BinaryDataDto;
import com.scalable.assignmentscalableweb.dto.RestDiffResponse;
import com.scalable.assignmentscalableweb.entities.BinaryData;
import com.scalable.assignmentscalableweb.enumerator.ResponseMessage;
import com.scalable.assignmentscalableweb.exception.*;
import com.scalable.assignmentscalableweb.repository.BinaryDataRepository;
import javafx.geometry.Side;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Binary data service class.
 */
@Slf4j
@Service
@SuppressWarnings("PMD.PreserveStackTrace")
public class BinaryDataService {

    private final BinaryDataRepository binaryDataRepository;

    /**
     * BinaryDataService Constructor.
     *
     * @param binaryDataRepository repository class
     */
    public BinaryDataService(BinaryDataRepository binaryDataRepository) {
        this.binaryDataRepository = binaryDataRepository;
    }

    /**
     * Update left/side binary data
     *
     * @param dataId        Binary data id.
     * @param binaryDataDto object to persist on database.
     * @param side          identify if data is left or side type.
     */
    public BinaryData updateBinaryDataSide(final int dataId, final BinaryDataDto binaryDataDto, final String side) {
        log.info("Update binary data with id {}", dataId);

        validateBinaryData(binaryDataDto.getData());
        try {
            BinaryData binaryData = findBinaryDataById(dataId);

            if (isNull(binaryData)) {
                log.info("Create new binary data on database");
                binaryData = new BinaryData(dataId);

            }

            log.info("Update side {} binary data with id {}", side, dataId);
            if (Side.LEFT.name().equals(side)) {
                binaryData.setLeftData(binaryDataDto.getData());
            } else {
                binaryData.setRightData(binaryDataDto.getData());
            }

            log.info("Save binary data on database");
            binaryData = binaryDataRepository.save(binaryData);
            return binaryData;
        } catch (Exception e) {
            log.error("Unexpected exception occurred in update binary data operation", e);
            throw new UnexpectedException();
        }

    }

    /**
     * Find binary data by id on database
     *
     * @param binaryDataId database id.
     */
    private BinaryData findBinaryDataById(final int binaryDataId) {
        log.info("Find binary data by id [{}]", binaryDataId);
        return binaryDataRepository.findByDataId(binaryDataId);
    }

    /**
     * Compare Base64 data
     *
     * @param binaryDataId is used by repository to find a object
     * @return true if left and right data are equals or throw if left and right data are not equals
     */
    public RestDiffResponse diffBinaryBase64Data(final int binaryDataId) {
        log.info("Validate left and right binary data are equals for binary data id i({})", binaryDataId);

        final BinaryData binaryData = findBinaryDataById(binaryDataId);

        if (isNull(binaryData)) {
            log.error("Binary data not found");
            throw new BinaryDataNotFoundException();
        }

        validateBinaryDataSides(binaryData);

        final RestDiffResponse restDiffResponse = new RestDiffResponse(binaryData.getDataId(), binaryData.getLeftData(),
                binaryData.getRightData());

        try {
            final byte[] leftData = binaryData.getLeftData().getBytes();
            final byte[] rightData = binaryData.getRightData().getBytes();

            if (Arrays.equals(leftData, rightData)) {
                log.info("Binary data left and right are equals");
                restDiffResponse.setMessage(ResponseMessage.LEFT_SIDE_AND_RIGHT_SIDE_ARE_EQUALS.getMessage());
            } else if (leftData.length != rightData.length) {
                log.info("Binary data left and right not have same size");
                restDiffResponse.setMessage(ResponseMessage.BINARY_DATA_LEFT_RIGHT_NOT_HAVE_SAME_SIZE.getMessage());
            } else {
                log.info("Process Offsets in left and right data");
                final List<Integer> offsetsList = getOffsetsList(leftData, rightData);
                restDiffResponse.setMessage(new StringBuilder().append(offsetsList.stream()
                        .map(offset -> offset.toString()).collect(Collectors.joining(",")))
                        .append(" - Length: ").append(leftData.length).toString());
            }

        } catch (Exception e) {
            log.error("Unexpected exception occurred in diff operation", e);
            throw new UnexpectedException();
        }

        return restDiffResponse;
    }

    /**
     * Validate binary data input
     *
     * @param data String data
     */
    private void validateBinaryData(final String data) {
        log.info("Validate binary data input");

        if (isBlank(data)) {
            log.error("The data input is blank");
            throw new BinaryDataIsBlankException();
        }

        try {
            Base64.getDecoder().decode(data);
        } catch (IllegalArgumentException e) {
            log.error("The data input is not a valid base64");
            throw new InvalidBase64BinaryDataException();
        }
    }

    /**
     * Get offsets list
     *
     * @param leftData data
     * @param leftData data
     * @return offsets list
     */
    private List<Integer> getOffsetsList(final byte[] leftData, final byte[] rightData) {
        log.info("Get offsets list");

        final List<Integer> offsetsList = new ArrayList<>();

        for (int index = 0; index < leftData.length; index++) {
            if (leftData[index] != rightData[index]) {
                offsetsList.add(index);
            }
        }

        return offsetsList;
    }

    /**
     * Validate binary data sides
     *
     * @param binaryData Base64 data
     */
    private void validateBinaryDataSides(final BinaryData binaryData) {
        log.info("Validate binary data sides");

        if (isBlank(binaryData.getLeftData())) {
            log.error("Left binary data not found");
            throw new LeftBinaryDataNotFoundException();
        }

        if (isBlank(binaryData.getRightData())) {
            log.error("Right binary data not found");
            throw new RightBinaryDataNotFoundException();
        }
    }

}
