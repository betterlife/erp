package io.betterlife.domains.finical;

import io.betterlife.domains.BaseObject;

import javax.persistence.*;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "ExpenseCategory.getById", query = "SELECT e FROM ExpenseCategory e WHERE e.id = :id ")
})
public class ExpenseCategory extends BaseObject {

    public ExpenseCategory() {
        System.out.println("Init Expense Category");
    }

    public ExpenseCategory(String categoryName) {
        this();
        setCategoryName(categoryName);
    }

    public void setCategoryName(String categoryName) {
        setValue("categoryName", categoryName);
    }

    public String getCategoryName() {
        return getValue("categoryName");
    }

}
