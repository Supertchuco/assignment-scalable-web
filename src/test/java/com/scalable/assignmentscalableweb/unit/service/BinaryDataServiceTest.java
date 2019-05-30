package com.scalable.assignmentscalableweb.unit.service;

import com.scalable.assignmentscalableweb.dto.BinaryDataDto;
import com.scalable.assignmentscalableweb.dto.RestDiffResponse;
import com.scalable.assignmentscalableweb.entities.BinaryData;
import com.scalable.assignmentscalableweb.enumerator.ResponseMessage;
import com.scalable.assignmentscalableweb.exception.BinaryDataIsBlankException;
import com.scalable.assignmentscalableweb.exception.InvalidBase64BinaryDataException;
import com.scalable.assignmentscalableweb.exception.LeftBinaryDataNotFoundException;
import com.scalable.assignmentscalableweb.exception.RightBinaryDataNotFoundException;
import com.scalable.assignmentscalableweb.repository.BinaryDataRepository;
import com.scalable.assignmentscalableweb.service.BinaryDataService;
import javafx.geometry.Side;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

/**
 * Binary data service unit tests.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class BinaryDataServiceTest {

    private static final String VALIDATE_BINARY_DATA_SIDES_METHOD = "validateBinaryDataSides";
    private static final String GET_OFFSETS_LIST_METHOD = "getOffsetsList";
    private static final String VALIDATE_BINARY_DATA_METHOD = "validateBinaryData";
    private static final String UPDATE_BINARY_DATA_METHOD = "updateBinaryDataSide";
    private static final String VALID_BASE64_BINARY_DATA = "VGVzdGVfYV92YWxpZF9iYXNlNjRfc3RyaW5n";

    private static final String INPUT_ONE = "dGVzdGUxaW5wdXQ=";
    private static final String INPUT_TWO = "dGVzdDA5aW5wdXQ=";
    private static final String INPUT_THREE = "dGVzdGlucHV0";

    private static final byte[] BYTES_ONE = {69, 121, 101, 45, 62, 118, 101, 114};
    private static final byte[] BYTES_TWO = {32, 121, 101, 41, 62, 111, 101, 114};

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BinaryData binaryData;
    private String data;

    @InjectMocks
    @Spy
    private BinaryDataService binaryDataService;

    @Mock
    private BinaryDataRepository binaryDataRepository;

    private Object[] inputArray;

    private RestDiffResponse restDiffResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test scenario when binary data is valid.
     */
    @Test
    public void shouldNotThrowExceptionWhenBinaryDataIsValid() {
        inputArray = new Object[]{new BinaryData(1, "right_data", "left_data")};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_SIDES_METHOD, inputArray);
    }

    /**
     * Test scenario when left data is null.
     */
    @Test(expected = LeftBinaryDataNotFoundException.class)
    public void shouldThrowLeftBinaryDataNotFoundExceptionWhenLeftDataIsNull() {
        inputArray = new Object[]{new BinaryData(1, null, "right_data")};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_SIDES_METHOD, inputArray);
    }

    /**
     * Test scenario when right data is null.
     */
    @Test(expected = RightBinaryDataNotFoundException.class)
    public void shouldThrowRightBinaryDataNotFoundExceptionWhenRightDataIsNull() {
        inputArray = new Object[]{new BinaryData(1, "left_data", null)};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_SIDES_METHOD, inputArray);
    }

    /**
     * Test scenario when left data is empty.
     */
    @Test(expected = LeftBinaryDataNotFoundException.class)
    public void shouldThrowLeftBinaryDataNotFoundExceptionWhenLeftDataIsEmpty() {
        inputArray = new Object[]{new BinaryData(1, StringUtils.EMPTY, "right_data")};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_SIDES_METHOD, inputArray);
    }

    /**
     * Test scenario when right data is empty.
     */
    @Test(expected = RightBinaryDataNotFoundException.class)
    public void shouldThrowRightBinaryDataNotFoundExceptionWhenRightDataIsEmpty() {
        inputArray = new Object[]{new BinaryData(1, "left_data", StringUtils.EMPTY)};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_SIDES_METHOD, inputArray);
    }

    /**
     * Test scenario when generate offset list with success.
     */
    @Test
    public void shouldGetOffsetsListWhenInputLeftDataAndRightData() {
        inputArray = new Object[]{BYTES_ONE, BYTES_TWO};
        assertEquals(Arrays.asList(0, 3, 5), ReflectionTestUtils
                .invokeMethod(binaryDataService, GET_OFFSETS_LIST_METHOD, inputArray));
    }

    /**
     * Test scenario when validate a valid base64 binary data.
     */
    @Test
    public void shouldNotThrowExceptionWhenValidateBinaryData() {
        inputArray = new Object[]{VALID_BASE64_BINARY_DATA};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_METHOD, inputArray);
    }

    /**
     * Test scenario when validate not valid base64 binary data.
     */
    @Test(expected = InvalidBase64BinaryDataException.class)
    public void shouldThrowInvalidBase64BinaryDataExceptionWhenDataIsNotBase64() {
        inputArray = new Object[]{"not_valid_base64_string"};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_METHOD, inputArray);
    }

    /**
     * Test scenario when validate blank base64 binary data.
     */
    @Test(expected = BinaryDataIsBlankException.class)
    public void shouldThrowBinaryDataIsBlankExceptionWhenDataIsEmpty() {
        inputArray = new Object[]{StringUtils.EMPTY};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_METHOD, inputArray);
    }

    /**
     * Test scenario when validate null base64 binary data.
     */
    @Test(expected = BinaryDataIsBlankException.class)
    public void shouldThrowBinaryDataIsBlankExceptionWhenDataIsNull() {
        inputArray = new Object[]{null};
        ReflectionTestUtils.invokeMethod(binaryDataService, VALIDATE_BINARY_DATA_METHOD, inputArray);
    }

    /**
     * Test scenario when binary data not exists on database and side is left.
     */
    @Test
    public void shouldNotThrowExceptionWhenBinaryDataNotExistsOnDatabaseAndSideIsLeft() {
        final BinaryDataDto binaryDataDto = new BinaryDataDto();
        binaryDataDto.setData(VALID_BASE64_BINARY_DATA);
        doReturn(new BinaryData(1)).when(binaryDataRepository).save(Mockito.any(BinaryData.class));
        inputArray = new Object[]{1, binaryDataDto, Side.LEFT.name()};
        assertEquals(new BinaryData(1, data, null), ReflectionTestUtils
                .invokeMethod(binaryDataService, UPDATE_BINARY_DATA_METHOD, inputArray));
    }

    /**
     * Test scenario when binary data exists on data base and side is left.
     */
    @Test
    public void shouldNotThrowExceptionWhenBinaryDataExistsOnDatabaseAndSideIsLeft() {
        final BinaryDataDto binaryDataDto = new BinaryDataDto();
        data = VALID_BASE64_BINARY_DATA;
        binaryDataDto.setData(data);
        binaryData = new BinaryData(1, data, null);
        doReturn(new BinaryData(1)).when(binaryDataRepository).findByDataId(1);
        doReturn(binaryData).when(binaryDataRepository).save(Mockito.any(BinaryData.class));
        inputArray = new Object[]{1, binaryDataDto, Side.LEFT.name()};
        assertEquals(new BinaryData(1, data, null), ReflectionTestUtils
                .invokeMethod(binaryDataService, UPDATE_BINARY_DATA_METHOD, inputArray));
    }

    /**
     * Test scenario when binary data not exists on database and side is right.
     */
    @Test
    public void shouldNotThrowExceptionWhenBinaryDataNotExistsOnDatabaseAndSideIsRight() {
        final BinaryDataDto binaryDataDto = new BinaryDataDto();
        data = VALID_BASE64_BINARY_DATA;
        binaryDataDto.setData(data);
        binaryData = new BinaryData(1, null, data);
        doReturn(binaryData).when(binaryDataRepository).save(Mockito.any(BinaryData.class));
        inputArray = new Object[]{1, binaryDataDto, Side.RIGHT.name()};
        assertEquals(binaryData, ReflectionTestUtils
                .invokeMethod(binaryDataService, UPDATE_BINARY_DATA_METHOD, inputArray));
    }

    /**
     * Test scenario when binary data not exists on database and side is right.
     */
    @Test
    public void shouldNotThrowExceptionWhenBinaryDataExistOnDatabaseAndSideIsRight() {
        final BinaryDataDto binaryDataDto = new BinaryDataDto();
        data = VALID_BASE64_BINARY_DATA;
        binaryDataDto.setData(data);
        binaryData = new BinaryData(1, null, data);
        doReturn(new BinaryData(1)).when(binaryDataRepository).findByDataId(1);
        doReturn(binaryData).when(binaryDataRepository).save(Mockito.any(BinaryData.class));
        inputArray = new Object[]{1, binaryDataDto, Side.RIGHT.name()};
        assertEquals(binaryData, ReflectionTestUtils
                .invokeMethod(binaryDataService, UPDATE_BINARY_DATA_METHOD, inputArray));
    }

    /**
     * Test scenario when binary data are equals.
     */
    @Test
    public void shouldNotThrowExceptionWhenBinaryDataAreEquals() {
        doReturn(new BinaryData(1, INPUT_ONE, INPUT_ONE))
                .when(binaryDataRepository).findByDataId(1);
        restDiffResponse = new RestDiffResponse(1, INPUT_ONE, INPUT_ONE);
        restDiffResponse.setMessage(ResponseMessage.LEFT_SIDE_AND_RIGHT_SIDE_ARE_EQUALS.getMessage());
        assertEquals(restDiffResponse, binaryDataService.diffBinaryBase64Data(1));
    }

    /**
     * Test scenario when binary data sizes are not equal.
     */
    @Test
    public void shouldReturnDifferentSizeMessageWhenBinaryDataSizesAreNotEquals() {
        doReturn(new BinaryData(1, INPUT_ONE, INPUT_THREE))
                .when(binaryDataRepository).findByDataId(1);
        restDiffResponse = new RestDiffResponse(1, INPUT_ONE, INPUT_THREE);
        restDiffResponse.setMessage(ResponseMessage.BINARY_DATA_LEFT_RIGHT_NOT_HAVE_SAME_SIZE.getMessage());
        assertEquals(restDiffResponse, binaryDataService.diffBinaryBase64Data(1));
    }

    /**
     * Test scenario when binary data are not equal.
     */
    @Test
    public void shouldReturnDifferentBinaryDataMessageWhenBinaryDataAreNotEquals() {
        doReturn(new BinaryData(1, INPUT_ONE, INPUT_TWO))
                .when(binaryDataRepository).findByDataId(1);
        restDiffResponse = new RestDiffResponse(1, INPUT_ONE, INPUT_TWO);
        restDiffResponse.setMessage("5,6,7 - Length: 16");
        assertEquals(restDiffResponse, binaryDataService.diffBinaryBase64Data(1));
    }

}
