package io.betterlife.util;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

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

    public JsonNode stringToJsonNode (String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(str);
    }

    public InputStream jsonNodeToInputStream(JsonNode node) {
        return IOUtils.toInputStream(node.toString());
    }
}
