package io.betterlife.util.rest;

import io.betterlife.util.IOUtil;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/12/14
 */
public class IOUtilTest {

    @Test
    public void testInputStreamToString() throws IOException {
        String input = "abc";
        InputStream inputStream = IOUtils.toInputStream(input);
        String output = IOUtil.getInstance().inputStreamToString(inputStream);
        assertEquals(input, output);
    }
    @Test
    public void testInputStreamToJson() throws IOException {
        Map<String, String> values = new HashMap<>();
        values.put("username", "uuuu");
        values.put("password", "ppppp");
        String str = new ObjectMapper().writeValueAsString(values);
        InputStream inputStream = IOUtils.toInputStream(str);
        Map<String, String> result = IOUtil.getInstance().inputStreamToJson(inputStream);
        assertEquals(2, result.size());
        assertEquals(values, result);
    }

    @Test
    public void testStringToInputStream() throws Exception {
        String content = "abc";
        InputStream stream = IOUtil.getInstance().stringToInputStream(content);
        String returnStr = IOUtils.toString(stream);
        assertEquals(content, returnStr);
    }
}
