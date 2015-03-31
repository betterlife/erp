package io.betterlife.framework.application.config;

import io.betterlife.framework.converter.Converter;
import io.betterlife.framework.converter.DefaultConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 * Application config
 */
public class ApplicationConfig {

    /**
     * Ignore field when output toString.
     */
    private static List<String> toStringIgnoreFields;

    /**
     * Application data source name
     */
    public static final String DataSourceName = "jdbc/PostgreSQLDS";

    /**
     * Application persistence unit name
     */
    public static final String PersistenceUnitName = "betterlife";
	

    /**
     * Table name prefix
     */
    public static final String TableNamePrefix = "BL_";
    public static final String DefaultRepresentField = "name";
    public static final int DefaultFieldRank = 0;
    public static final Class<? extends Converter> DefaultConverterClass = DefaultConverter.class;
    public static final int MaxNumberOfObjectForSelectController = 10;

    public static String getLocale() {return "zh_CN";}

    public static boolean isDevelopmentMode() {
        return true;
    }

    public static List<String> getToStringIgnoreFields() {
        if (null == toStringIgnoreFields) {
            toStringIgnoreFields = new ArrayList<>();
            toStringIgnoreFields.add("lastModify");
            toStringIgnoreFields.add("creator");
        }
        return toStringIgnoreFields;
    }
}
