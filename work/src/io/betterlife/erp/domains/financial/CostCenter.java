package io.betterlife.erp.domains.financial;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.annotation.FormField;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CostCenter.getById", query = "SELECT c FROM CostCenter c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "CostCenter.getAll",  query = "SELECT c FROM CostCenter c WHERE c.active = TRUE"),
    @NamedQuery(name = "CostCenter.getByKeyword", query = "SELECT c from CostCenter c WHERE c.name like :keyword and c.active = TRUE")
})
public class CostCenter extends BaseObject {

    public CostCenter(){}

    public CostCenter(String name) {
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
