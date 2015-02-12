package io.betterlife.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.betterlife.framework.util.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonUtilsTest {

    JsonUtils jsonUtil = JsonUtils.getInstance();

    @Test
    public void testStringToJsonNode() throws Exception {
        String str = "{\"id\" : 1, \"name\" : \"lxq\", \"active\" : true, " +
            "\"expense\" : {\"amount\" : 20.05}, " +
            "\"incoming\" : [{\"type\" : \"实体店\"}, {\"type\" : \"网店\"}]}";
        JsonNode node = jsonUtil.stringToJsonNode(str);
        assertNotNull(node);
        assertEquals(1, node.get("id").asInt());
        assertEquals("lxq", node.get("name").asText());
        assertEquals(true, node.get("active").asBoolean());
        assertNotNull(node.get("expense"));
        assertEquals(20.05, node.get("expense").get("amount").asDouble(), 0.00001);
        assertNotNull(node.get("incoming"));
        assertEquals("实体店", node.get("incoming").get(0).get("type").asText());
        assertEquals("网店", node.get("incoming").get(1).get("type").asText());
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(jsonUtil);
    }
}