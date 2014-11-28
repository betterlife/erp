package io.betterlife.util;

import clover.org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/28/14
 */
public class TemplateUtils {
    private static TemplateUtils instance = new TemplateUtils();
    private static final Logger logger = LogManager.getLogger(TemplateUtils.class.getName());

    private TemplateUtils(){}

    public static TemplateUtils getInstance() {
        return instance;
    }

    public String getHtmlTemplate(ServletContext context, String filePath) {
        try {
            String realPath = context.getRealPath(filePath);
            return FileUtils.readFileToString(new File(realPath), "UTF-8");
        } catch (Exception e) {
            logger.warn(String.format("Failed to get resource for file[%s], Returning empty string", filePath));
            return StringUtils.EMPTY;
        }
    }

    public String getNgModelNameForField(String key) {
        return "entity." + key;
    }

}
