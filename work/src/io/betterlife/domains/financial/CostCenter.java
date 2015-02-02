package io.betterlife.domains.financial;

import io.betterlife.domains.BaseObject;
import io.betterlife.rest.Form;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CostCenter.getById", query = "SELECT c FROM CostCenter c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "CostCenter.getAll",  query = "SELECT c FROM CostCenter c WHERE c.active = TRUE")
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

    @Form(DisplayRank = 5)
    public String getName() {
        return getValue("name");
    }

}
