package io.betterlife.application;

import io.betterlife.application.config.ApplicationConfig;
import io.betterlife.util.BLStringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Lawrence Liu
 * Date: 12/22/14
 */
public class I18n {

    private final Logger logger = LogManager.getLogger(I18n.class.getName());
    private boolean loaded = false;
    private String i18nPath;
    private Map<String, Map<String, String>> translations = new HashMap<>();

    private static I18n instance = new I18n();
    private I18n(){}

    public static I18n getInstance() {
        return I18n.instance;
    }

    public static void setInstance(I18n instance) {
        I18n.instance = instance;
    }

    public String get(String key, String locale) {
        if (!loaded || ApplicationConfig.isDevelopmentMode()) {
            loadTranslations(locale);
            loaded = true;
        }
        String result =  translations.get(locale).get(key);
        return (null == result) ? BLStringUtils.capitalize(key) : result;
    }

    private void loadTranslations(String locale) {
        String i18nPath = getI18nPath();
        File i18nFileForLocale = new File(i18nPath + File.separator + locale + ".csv");
        final HashMap<String, String> localeTrans = new HashMap<>();
        translations.put(locale, localeTrans);
        if (i18nFileForLocale.exists()) {
            try {
                final FileInputStream input = new FileInputStream(i18nFileForLocale);
                List<String> entries = IOUtils.readLines(input, BLStringUtils.ENCODING_UTF8);
                if (entries != null && entries.size() > 0) {
                    for (String entry : entries) {
                        String[] pair = entry.split(BLStringUtils.COMMA);
                        localeTrans.put(pair[0], pair[1]);
                    }
                }
            } catch (IOException e) {
                logger.fatal(String.format("Translation file[%s] not found for locale[%s] ",
                                           i18nFileForLocale, locale));
            }
        }
    }

    private String getI18nPath() {
        return i18nPath;
    }

    public String getFieldLabel(String entityType, String key, String locale) {
        String result;
        if (null == key) {
            result = BLStringUtils.EMPTY;
        } else {
            final String keyWithEntity = BLStringUtils.capitalize(entityType) + "." + key;
            result = get(keyWithEntity, locale);
            if (null == result || keyWithEntity.equals(result)) {
                result = get(key, locale);
            }
        }
        return null == result ? BLStringUtils.capitalize(key) : result;
    }

    public void initResources(ServletContext servletContext) {
        i18nPath = servletContext.getRealPath("/WEB-INF/classes/resources/i18n");
    }
}
