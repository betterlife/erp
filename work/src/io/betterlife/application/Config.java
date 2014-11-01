package io.betterlife.application;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 * Application config
 */
public interface Config {

    /**
     * Application data source name
     */
    String DataSourceName = "jdbc/betterlife-sqld";

    /**
     * Application persistence unit name
     */
    String PersistenceUnitName = "betterlife";

    /**
     * Table name prefix
     */

    String TableNamePrefix = "BL_";
}
