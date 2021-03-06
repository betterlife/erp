package io.betterlife.util.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.betterlife.framework.rest.ExecuteResult;
import io.betterlife.framework.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        JsonNode actualObj = JsonUtils.getInstance().stringToJsonNode(str);
        JsonNode expectObj = JsonUtils.getInstance().stringToJsonNode(expect);
        assertEquals(null, result.getResult());
        assertEquals(null, result.getSuccessMessage());
        assertEquals(true, result.isSuccess());
        assertEquals(expectObj, actualObj);
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    public void testReturnEmptyStringUponNullValueAsString() throws IOException {
        ObjectMapper mapper = mock(ObjectMapper.class);
        Object obj = new Object();
        ExecuteResult<Object> result = new ExecuteResult<>();
        when(mapper.writeValueAsString(result)).thenReturn(null);
        result.setObjectMapper(mapper);
        String resultStr = result.getRestString(obj);
        assertEquals(StringUtils.EMPTY, resultStr);
    }
}
