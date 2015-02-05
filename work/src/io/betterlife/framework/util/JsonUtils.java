package io.betterlife.framework.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/15/14
 */
public class JsonUtils {
    private static JsonUtils ourInstance = new JsonUtils();

    public static JsonUtils getInstance() {
        return ourInstance;
    }

    private JsonUtils() {
    }

    public <T> String objectToJsonString(T object) throws IOException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public JsonNode stringToJsonNode (String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(str);
    }

    public InputStream jsonNodeToInputStream(JsonNode node) {
        return IOUtils.toInputStream(node.toString());
    }
}
