package com.scalable.assignmentscalableweb.controle;

import com.scalable.assignmentscalableweb.dto.BinaryDataDto;
import com.scalable.assignmentscalableweb.dto.RestDiffResponse;
import com.scalable.assignmentscalableweb.entities.BinaryData;
import com.scalable.assignmentscalableweb.enumerator.DataSide;
import com.scalable.assignmentscalableweb.exception.ExceptionResponse;
import com.scalable.assignmentscalableweb.service.BinaryDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller for Binary Data.
 */
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Base 64 Binary Data")
@Slf4j
@SuppressWarnings("PMD.ExcessiveImports")
public class BinaryDataController {

    @Autowired
    private BinaryDataService binaryDataService;

    /**
     * Create left data and input on database.
     *
     * @param binaryDataDto request data input
     * @return 200 success
     */
    @RequestMapping(value = "/diff/{binaryDataId}/left", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Input left binary data.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = BinaryData.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
        @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = BinaryData.class)
    })
    public BinaryData createLeftBinaryData(@PathVariable int binaryDataId, @Valid @RequestBody BinaryDataDto binaryDataDto) {
        log.info("Create left binary data with id:{}", binaryDataId);
        return binaryDataService.updateBinaryDataSide(binaryDataId, binaryDataDto, DataSide.LEFT.name());
    }

    /**
     * Create right data and input on database.
     *
     * @param binaryDataDto request data input
     * @return 200 success
     */
    @RequestMapping(value = "/diff/{binaryDataId}/right", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Input right binary data.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = BinaryData.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
        @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = BinaryData.class)
    })
    public BinaryData createRightBinaryData(@PathVariable int binaryDataId, @Valid @RequestBody BinaryDataDto binaryDataDto) {
        log.info("Create right binary data with id:{}", binaryDataId);
        return binaryDataService.updateBinaryDataSide(binaryDataId, binaryDataDto, DataSide.RIGHT.name());
    }

    /**
     * Diff left and right data.
     *
     * @param binaryDataId request binary data id
     * @return 200 success
     */
    @RequestMapping(value = "/diff/{binaryDataId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Diff right and left binary data.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = RestDiffResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class),
        @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = RestDiffResponse.class)
    })
    public RestDiffResponse diffBinaryData(@PathVariable int binaryDataId) {
        log.info("Diff binary data with id:{}", binaryDataId);
        return binaryDataService.diffBinaryBase64Data(binaryDataId);
    }

}
