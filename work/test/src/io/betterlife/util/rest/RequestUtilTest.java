package io.betterlife.util.rest;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/12/14
 */
public class RequestUtilTest {

    @Test
    public void testInputStreamToString() throws IOException {
        String input = "abc";
        InputStream inputStream = IOUtils.toInputStream(input);
        String output = RequestUtil.getInstance().inputStreamToString(inputStream);
        assertEquals(input, output);
    }
    @Test
    public void testRequestToJson() throws IOException {
        final String username = "uuuu";
        final String password = "ppppp";
        final String usernameKey = "username";
        final String passwordKey = "password";
        String input = "{\"" + usernameKey + "\" : \"" + username + "\", \"" + passwordKey + "\" : \"" + password + "\"}";
        InputStream inputStream = IOUtils.toInputStream(input);
        Map<String, String> result = RequestUtil.getInstance().requestToJson(inputStream);
        assertEquals(2, result.size());
        assertEquals(username, result.get(usernameKey));
        assertEquals(password, result.get(passwordKey));
    }
}
