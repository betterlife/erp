package io.betterlife.domains.financial;

import io.betterlife.domains.BaseObject;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "IncomingCategory.getById", query = "SELECT e FROM IncomingCategory e WHERE e.id = :id AND e.active = TRUE"),
    @NamedQuery(name = "IncomingCategory.getAll",  query = "SELECT e FROM IncomingCategory e WHERE e.active = TRUE")
})
public class IncomingCategory extends BaseObject {

    public IncomingCategory() {}

    public IncomingCategory(String name) {
        this();
        setName(name);
    }

    public void setName(String name) {
        setValue("name", name);
    }

    public String getName() {
        return getValue("name");
    }

}
