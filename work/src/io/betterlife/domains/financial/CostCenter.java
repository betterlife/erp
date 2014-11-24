package io.betterlife.domains.financial;

import io.betterlife.domains.BaseObject;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CostCenter.getById", query = "SELECT c FROM CostCenter c WHERE c.id = :id "),
    @NamedQuery(name = "CostCenter.getAll",  query = "SELECT c FROM CostCenter c")
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

    public String getName() {
        return getValue("name");
    }

}
