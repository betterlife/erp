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
    private List<String> toStringIgnoreFields;

    /**
     * Application data source name
     */
    public final String DataSourceName = "jdbc/PostgreSQLDS";

    /**
     * Application persistence unit name
     */
    public final String PersistenceUnitName = "betterlife";
	

    /**
     * Table name prefix
     */
    public final String TableNamePrefix = "BL_";
    public final static String DefaultRepresentField = "name";
    public final static int DefaultFieldRank = 0;
    public final static String DefaultDetailField = "detail";
    public final boolean DefaultVisible = true;
    public final Class<? extends Converter> DefaultConverterClass = DefaultConverter.class;
    public final int MaxNumberOfObjectForSelectController = 10;
    private static ApplicationConfig instance = new ApplicationConfig();

    public static ApplicationConfig getInstance(){
        return instance;
    }

    public static void setInstance(ApplicationConfig config) {
        instance = config;
    }

    public String getLocale() {return "zh_CN";}

    public boolean isDevelopmentMode() {
        return true;
    }

    public List<String> getToStringIgnoreFields() {
        if (null == toStringIgnoreFields) {
            toStringIgnoreFields = new ArrayList<>();
            toStringIgnoreFields.add("lastModify");
            toStringIgnoreFields.add("creator");
        }
        return toStringIgnoreFields;
    }
}
