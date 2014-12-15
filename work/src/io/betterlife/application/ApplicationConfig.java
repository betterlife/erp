package io.betterlife.application;

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

    /** Register all entities, this registry will be used for
     *  Generate Entity CRUD rest services
     *  Generate meta data for creation of front end CRUD UI.
     */
    public static void registerEntities(){
        ServiceEntityManager.getInstance().registerServiceEntity("fundCategory", FundCategory.class);
        ServiceEntityManager.getInstance().registerServiceEntity("user", User.class);
        ServiceEntityManager.getInstance().registerServiceEntity("costCenter", CostCenter.class);
        ServiceEntityManager.getInstance().registerServiceEntity("fund", Fund.class);
    }

    public static String getDefaultRepresentField(){
        return "name";
    }
}
