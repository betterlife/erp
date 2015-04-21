package io.betterlife.framework.application;

import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.util.BLStringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Lawrence Liu
 * Date: 12/22/14
 */
public class I18n {

    private final Logger logger = LogManager.getLogger(I18n.class.getName());
    private Map<String, Map<String, String>> translations = new HashMap<>();
    private List<String> resources = new ArrayList<>(4);
    private ServletContext context;
    private ApplicationConfig config;

    private static I18n instance = new I18n();
    private I18n(){}

    public static I18n getInstance() {
        return I18n.instance;
    }

    public static void setInstance(I18n instance) {
        I18n.instance = instance;
    }

    public String get(String key, String locale) {
        if (null == key) {
            key = "null";
        }
        if (getConfig().isDevelopmentMode()) {
            for (String resource : resources) {
                try {
                    loadResource(context, resource);
                } catch (IOException e) {
                    logger.warn("Failed to load resource[%s]", resource);
                }
            }
        }
        String result = translations.get(locale).get(key);
        return (null == result) ? BLStringUtils.capitalize(key) : result;
    }

    private int loadTranslations(InputStream stream, String locale) throws IOException {
        int count = 0;
        final HashMap<String, String> localeTrans = new HashMap<>();
        translations.put(locale, localeTrans);
        List<String> entries = IOUtils.readLines(stream);
        if (entries != null && entries.size() > 0) {
            for (String entry : entries) {
                String[] pair = entry.split(BLStringUtils.COMMA);
                localeTrans.put(pair[0], pair[1]);
                count ++;
            }
        }
        return count;
    }

    private String getFieldLabel(String entityType, String key, String locale) {
        String result;
        if (null == key) {
            key = "null";
        }
        final String keyWithEntity = BLStringUtils.capitalize(entityType) + "." + key;
        result = get(keyWithEntity, locale);
        if (null == result || keyWithEntity.equals(result)) {
            result = get(key, locale);
        }
        return result;
    }

    public int initResources(ServletContext servletContext) throws IOException {
        final String resourceName = "/WEB-INF/classes/resources/i18n/" + getConfig().getLocale() + ".csv";
        resources.add(resourceName);
        this.context = servletContext;
        return loadResource(servletContext, resourceName);
    }

    public int loadResource(ServletContext servletContext, String resourceName) throws IOException {
        InputStream stream = servletContext.getResourceAsStream(resourceName);
        return loadTranslations(stream, getConfig().getLocale());
    }

    public String getFieldLabel(String entityType, String key) {
        return I18n.getInstance().getFieldLabel(entityType, key, getConfig().getLocale());
    }

    public void setConfig(ApplicationConfig config) {
        this.config = config;
    }

    public ApplicationConfig getConfig(){
        if (null == this.config){
            return ApplicationConfig.getInstance();
        }
        return this.config;
    }
}
