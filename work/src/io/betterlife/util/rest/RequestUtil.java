package io.betterlife.util.rest;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/12/14
 */
public class RequestUtil {
    private static RequestUtil ourInstance = new RequestUtil();
    private static final Logger logger = LogManager.getLogger(RequestUtil.class.getName());

    public static RequestUtil getInstance() {
        return ourInstance;
    }

    private RequestUtil() {
    }

    public String inputStreamToString(InputStream requestBody) throws IOException {
        return IOUtils.toString(requestBody, "UTF-8");
    }

    public Map<String, String> requestToJson(InputStream requestBody) throws IOException {
        String requestStr = inputStreamToString(requestBody);
        Map<String,String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(requestStr, new TypeReference<HashMap<String,String>>(){});
        } catch (Exception e) {
            logger.error(String.format("Error to read String[%s] to map", requestStr), e);
        }
        return map;
    }
}
