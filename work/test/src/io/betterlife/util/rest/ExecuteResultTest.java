package io.betterlife.util.rest;

import org.apache.wink.json4j.JSONObject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
public class ExecuteResultTest {
    @Test public void testDefaultIsSuccess() {
        ExecuteResult result = new ExecuteResult();
        assertEquals(true, result.isSuccess());
    }

    @Test public void testGetRestStringOfNull() throws IOException {
        ExecuteResult<Object> result = new ExecuteResult<>();
        result.setResult(null);
        String str = result.getRestString(null);
        String expect = "{\"success\":true,\"result\":null,\"successMessage\":null,\"errorMessages\":[]}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(str);
        JsonNode expectObj = mapper.readTree(expect);
        assertEquals(null, result.getResult());
        assertEquals(null, result.getSuccessMessage());
        assertEquals(true, result.isSuccess());
        assertEquals(expectObj, actualObj);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    public void testReturnEmptyStringUponException() throws IOException {
        ObjectMapper mapper = mock(ObjectMapper.class);
        Object obj = new Object();
        ExecuteResult<Object> result = new ExecuteResult<>();
        when(mapper.writeValueAsString(result)).thenThrow(new IOException());
        result.setObjectMapper(mapper);
        String resultStr = result.getRestString(obj);
        assertEquals("", resultStr);
    }

    @Test
    public void testReturnEmptyStringUponNullValueAsString() throws IOException {
        ObjectMapper mapper = mock(ObjectMapper.class);
        Object obj = new Object();
        ExecuteResult<Object> result = new ExecuteResult<>();
        when(mapper.writeValueAsString(result)).thenReturn(null);
        result.setObjectMapper(mapper);
        String resultStr = result.getRestString(obj);
        assertEquals("", resultStr);
    }
}
