package io.betterlife.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/12/14
 */
public class IOUtil {
    private static IOUtil ourInstance = new IOUtil();
    private static final Logger logger = LogManager.getLogger(IOUtil.class.getName());

    public static IOUtil getInstance() {
        return ourInstance;
    }

    private IOUtil() {
    }

    public String inputStreamToString(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream, "UTF-8");
    }

    public InputStream stringToInputStream(String content) throws IOException {
        return IOUtils.toInputStream(content);
    }

    public Map<String, Object> inputStreamToJson(InputStream requestBody) throws IOException {
        String requestStr = inputStreamToString(requestBody);
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(requestStr, new TypeReference<HashMap<String, Object>>(){});
        } catch (Exception e) {
            logger.error(String.format("Error to read String[%s] to map", requestStr), e);
        }
        return map;
    }

    public String writeToAbsolutePath(final String absolutePath, final String fileContent,
                                       final String encoding) throws IOException {
        FileOutputStream outputStream  = null;
        try {
            outputStream = FileUtils.openOutputStream(new File(absolutePath));
            IOUtils.write(fileContent, outputStream, encoding);
        } finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
        return fileContent;
    }
}
