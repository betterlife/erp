package io.betterlife.domains.finical;

import io.betterlife.domains.BaseObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@Entity
public class CostCenter extends BaseObject {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    private String name;

    public CostCenter(){}

    public CostCenter(String name) {
        this();
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
