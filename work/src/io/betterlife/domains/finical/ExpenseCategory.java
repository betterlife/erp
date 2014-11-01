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
public class ExpenseCategory extends BaseObject {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    private String categoryName;

    public ExpenseCategory(){}

    public ExpenseCategory(String categoryName) {
        this();
        setCategoryName(categoryName);
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
