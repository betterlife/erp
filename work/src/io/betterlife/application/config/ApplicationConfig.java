package io.betterlife.application.config;

import io.betterlife.application.manager.ServiceEntityManager;
import io.betterlife.domains.financial.CostCenter;
import io.betterlife.domains.financial.Fund;
import io.betterlife.domains.financial.FundCategory;
import io.betterlife.domains.security.User;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 * Application config
 */
public class ApplicationConfig {

    /**
     * Application data source name
     */
    public static final String DataSourceName = "jdbc/betterlife-sqld";

    /**
     * Application persistence unit name
     */
    public static final String PersistenceUnitName = "betterlife";
	

    /**
     * Table name prefix
     */
    public static final String TableNamePrefix = "BL_";

    public static String getDefaultRepresentField(){
        return "name";
    }

    public static String getLocale() {return "zh_CN";}

    public static boolean isDevelopmentMode() {
        return true;
    }
}
