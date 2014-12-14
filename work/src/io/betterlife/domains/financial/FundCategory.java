package io.betterlife.domains.financial;

import io.betterlife.domains.BaseObject;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "FundCategory.getById", query = "SELECT e FROM FundCategory e WHERE e.id = :id "),
    @NamedQuery(name = "FundCategory.getAll",  query = "SELECT e FROM FundCategory e")
})
public class FundCategory extends BaseObject {

    public FundCategory() {}

    public FundCategory(String name) {
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
