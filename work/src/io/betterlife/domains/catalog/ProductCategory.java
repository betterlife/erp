package io.betterlife.domains.catalog;

import io.betterlife.domains.BaseObject;
import io.betterlife.rest.Form;

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
    @NamedQuery(name = "ProductCategory.getAll",  query = "SELECT c FROM ProductCategory c WHERE c.active = TRUE")
})
public class ProductCategory extends BaseObject {

    @Form(DisplayRank = 3)
    public String getCode() {return getValue("code");}

    public void setCode(String code) {
            setValue("code", code);
    }

    @Form(DisplayRank = 5)
    public String getName() {
            return getValue("name");
        }

    public void setName(String name) {
            setValue("name", name);
        }

    @Form(DisplayRank = 10)
    @ManyToOne
    public ProductCategory getParentCategory(){
        return getValue("parentCategory");
    }

    public void setParentCategory(ProductCategory productCategory) {
        setValue("parentCategory", productCategory);
    }
}
