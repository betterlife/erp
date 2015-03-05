package io.betterlife.erp.domains.catalog;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.annotation.FormField;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Author: Lawrence Liu
 * Date: 1/13/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "ProductCategory.getById", query = "SELECT c FROM ProductCategory c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "ProductCategory.getAll",  query = "SELECT c FROM ProductCategory c WHERE c.active = TRUE"),
    @NamedQuery(name = "ProductCategory.getByKeyword",  query = "SELECT c FROM ProductCategory c WHERE c.active = TRUE and (c.code like :keyword or c.name like :keyword)")
})
public class ProductCategory extends BaseObject {

    @FormField(DisplayRank = 3)
    public String getCode() {return getValue("code");}

    public void setCode(String code) {
            setValue("code", code);
    }

    @FormField(DisplayRank = 5)
    public String getName() {
            return getValue("name");
        }

    public void setName(String name) {
            setValue("name", name);
        }

    @FormField(DisplayRank = 10)
    @ManyToOne
    public ProductCategory getParentCategory(){
        return getValue("parentCategory");
    }

    public void setParentCategory(ProductCategory productCategory) {
        setValue("parentCategory", productCategory);
    }
}
