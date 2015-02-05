package io.betterlife.erp.domains.financial;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.annotation.FormField;

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

    @FormField(DisplayRank = 5)
    public String getName() {
        return getValue("name");
    }

}
