package io.betterlife.framework.persistence;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */
public class NamedQueryRules {
    private static NamedQueryRules instance = new NamedQueryRules();

    private NamedQueryRules(){}

    public static NamedQueryRules getInstance() {
        return instance;
    }

    public static void setInstance(NamedQueryRules instance) {
        NamedQueryRules.instance = instance;
    }

    public String getIdQueryForEntity(String entityName){
        return StringUtils.capitalize(entityName) + ".getById";
    }

    public String getAllQueryForEntity(String entityName) {
        return StringUtils.capitalize(entityName) + ".getAll";
    }
}
