package com.scalable.assignmentscalableweb.integrantion;

import com.scalable.assignmentscalableweb.enumerator.ErrorMessage;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration for API test implementation.
 */
@ActiveProfiles("test")
@Sql({"/sql/purge.sql", "/sql/seed.sql"})
@RunWith(SpringRunner.class)
@SuppressWarnings("PMD.TooManyMethods")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BinaryDataIntegrationTest {

    private static final String BASE_ENDPOINT = "http://localhost:8090/v1/diff/";
    private static final String RIGHT_SIDE = "/right";
    private static final String LEFT_SIDE = "/left";
    private static final String ONE_SLICE = "/1";
    private static final String BINARY_DATA_1 = "request/binaryData1.json";

    private static final String RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_NOT_HAVE_SAME_SIZE = "{\"id\":1,\"leftSide\":\"YmluYXJ5RGF0YTFUZXN0\""
        + ",\"rightSide\":\"YmluYXJ5RGF0YTJUZXN0QWdvcmFWYWk=\",\"message\":\"Binary data left and right not have same size.\"}";
    private static final String RESPONSE_BINARY_DATA_LEFT_SIDE_1 = "{\"dataId\":1,\"leftData\":\"YmluYXJ5RGF0YTFUZXN0\",\"rightData\":null}";
    private static final String RESPONSE_BINARY_DATA_LEFT_1_AND_RIGHT_2_SIDES = "{\"dataId\":1,\"leftData\":\"YmluYXJ5RGF0YTFUZXN0\","
        + "\"rightData\":\"YmluYXJ5RGF0YTJUZXN0QWdvcmFWYWk=\"}";
    private static final String RESPONSE_BINARY_DATA_RIGHT_SIDE_2 = "{\"dataId\":1,\"leftData\":null,\"rightData\":\"YmluYXJ5RGF0YTJUZXN0Q"
        + "WdvcmFWYWk=\"}";
    private static final String RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_SIDE_3 = "{\"dataId\":1,\"leftData\":\"YmluYXJ5RGF0YTJUZXN0QWdvc"
        + "mFOYW8=\",\"rightData\":\"YmluYXJ5RGF0YTJUZXN0QWdvcmFWYWk=\"}";
    private static final String RESPONSE_BINARY_DATA_DIFFERENT_AND_SAME_SIZE = "{\"id\":1,\"leftSide\":\"YmluYXJ5RGF0YTJUZXN0QWdvcmFOYW"
        + "8=\",\"rightSide\":\"YmluYXJ5RGF0YTJUZXN0QWdvcmFWYWk=\",\"message\":\"27,30 - Length: 32\"}";
    private static final String RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_SIDES_1 = "{\"dataId\":1,\"leftData\":\"YmluYXJ5RGF0YTFUZXN0\""
        + ",\"rightData\":\"YmluYXJ5RGF0YTFUZXN0\"}";
    private static final String RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_SIDES_ARE_EQUALS = "{\"id\":1,\"leftSide\":\"YmluYXJ5RGF0YTFUZXN0\""
        + ",\"rightSide\":\"YmluYXJ5RGF0YTFUZXN0\",\"message\":\"Binary data left and right are equals.\"}";
    private static final String RESPONSE_BINARY_DATA_LEFT_SIDE_3 = "{\"dataId\":1,\"leftData\":\"YmluYXJ5RGF0YTJUZXN0QWdvcmFOYW8=\""
        + ",\"rightData\":null}";
    private static final String RESPONSE_BINARY_DATA_RIGHT_SIDE_3 = "{\"dataId\":1,\"leftData\":null,\"rightData\":\"YmluYXJ5RGF0YTJ"
        + "UZXN0QWdvcmFOYW8=\"}";
    private static final String RESPONSE_BINARY_DATA_RIGHT_SIDE_1 = "{\"dataId\":1,\"leftData\":null,\"rightData\":\"YmluYXJ5RGF"
        + "0YTFUZXN0\"}";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String payload;

    private HttpEntity<String> entity;

    private ResponseEntity<String> response;

    /**
     * Read json.
     *
     * @param filename file name input
     * @return String file content
     */
    private static String readJson(final String filename) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + filename), "UTF-8");
        } catch (IOException exception) {
            return null;
        }
    }

    /**
     * Build Http headers.
     *
     * @return Http Headers object
     */
    private HttpHeaders buildHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Test scenario when binary data have left and right different size.
     */
    @Test
    public void shouldReturn200AndDifferentLeftAndRightSizeMessageWhenBinaryDataHaveLeftAndRightDifferentSize() {
        payload = readJson(BINARY_DATA_1);
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_SIDE_1, response.getBody());

        payload = readJson("request/binaryData2.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + RIGHT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_1_AND_RIGHT_2_SIDES, response.getBody());

        entity = new HttpEntity<String>(buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_NOT_HAVE_SAME_SIZE, response.getBody());
    }

    /**
     * Test scenario when left And right binary data are Different.
     */
    @Test
    public void shouldReturn200AndOffsetsListMessageWhenLeftAndRightBinaryDataAreDifferent() {
        payload = readJson("request/binaryData2.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + RIGHT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_RIGHT_SIDE_2, response.getBody());

        payload = readJson("request/binaryData3.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_SIDE_3, response.getBody());

        entity = new HttpEntity<String>(buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_DIFFERENT_AND_SAME_SIZE, response.getBody());
    }

    /**
     * Test scenario when left and right binary data are equals.
     */
    @Test
    public void shouldReturn200AndLeftAndRightSidesAreEqualsMessageWhenLeftAndRightBinaryDataAreEquals() {
        payload = readJson(BINARY_DATA_1);
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_SIDE_1, response.getBody());

        payload = readJson(BINARY_DATA_1);
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + RIGHT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_SIDES_1, response.getBody());

        entity = new HttpEntity<String>(buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_AND_RIGHT_SIDES_ARE_EQUALS, response.getBody());
    }

    /**
     * Test scenario when insert left side data two times.
     */
    @Test
    public void shouldReturn200AndCreateAndUpdateLeftWhenInsertLeftSideTwoTimes() {
        payload = readJson(BINARY_DATA_1);
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_SIDE_1, response.getBody());

        payload = readJson("request/binaryData3.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_LEFT_SIDE_3, response.getBody());
    }

    /**
     * Test scenario when insert right side data two times.
     */
    @Test
    public void shouldReturn200AndCreateAndUpdateRightWhenInsertRightSideTwoTimes() {
        payload = readJson(BINARY_DATA_1);
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + RIGHT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_RIGHT_SIDE_1, response.getBody());

        payload = readJson("request/binaryData3.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + RIGHT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BINARY_DATA_RIGHT_SIDE_3, response.getBody());
    }

    /**
     * Test scenario when insert invalid base64 binary data.
     */
    @Test
    public void shouldReturn400AndInvalidBase64BinaryDataMessageWhenInsertInvalidBase64BinaryData() {
        payload = readJson("request/invalidBinaryData.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(contains(response.getBody(), ErrorMessage.INVALID_BASE64_BINARY_DATA_INPUT.getError()));
    }

    /**
     * Test scenario when insert Blank Base 64 binary data.
     */
    @Test
    public void shouldReturn400AndBlankBase64BinaryDataMessageWhenInsertBlankBase64BinaryData() {
        payload = readJson("request/BlankBinaryData.json");
        entity = new HttpEntity<String>(payload, buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE + LEFT_SIDE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(contains(response.getBody(), ErrorMessage.BLANK_BASE64_BINARY_DATA_INPUT.getError()));
    }

    /**
     * Test scenario when find binary data return Null.
     */
    @Test
    public void shouldReturn400AndBinaryDataNullMessageWhenFindBinaryDataReturnNull() {
        entity = new HttpEntity<String>(buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + ONE_SLICE, HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(contains(response.getBody(), ErrorMessage.BINARY_DATA_NOT_FOUND.getError()));
    }

    /**
     * Test scenario when left binary data side is Null.
     */
    @Test
    public void shouldReturn400AndLeftBinaryDataSideIsNullMessageWhenLeftBinaryDataSideIsNull() {
        entity = new HttpEntity<String>(buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + "/3", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(contains(response.getBody(), ErrorMessage.LEFT_DATA_NOT_FOUND.getError()));
    }

    /**
     * Test scenario when right binary data side is Null.
     */
    @Test
    public void shouldReturn400AndRightBinaryDataSideIsNullMessageWhenRightBinaryDataSideIsNull() {
        entity = new HttpEntity<String>(buildHttpHeaders());
        response = testRestTemplate.exchange(BASE_ENDPOINT + "/2", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(contains(response.getBody(), ErrorMessage.RIGHT_DATA_NOT_FOUND.getError()));
    }

}
