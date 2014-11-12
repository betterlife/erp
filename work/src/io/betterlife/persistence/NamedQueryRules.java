package io.betterlife.persistence;

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

    public String getIdQueryForEntity(String entityName){
        return entityName + ".getById";
    }

    public String getAllQueryForEntity(String entityName) {
        return entityName + ".getAll";
    }
}
