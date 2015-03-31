package io.betterlife.erp.domains.catalog;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.erp.condition.ProductCodeEditableCondition;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Product.getById", query = "SELECT c FROM Product c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "Product.getAll",  query = "SELECT c FROM Product c WHERE c.active = TRUE ORDER BY c.id DESC"),
    @NamedQuery(name = "Product.getByKeyword",  query = "SELECT c FROM Product c WHERE c.active = TRUE and (c.code like :keyword or c.name like :keyword or c.distinguishingFeature like :keyword or c.link like :keyword)")
})
public class Product extends BaseObject {

    @FormField(DisplayRank = 4, Editable = ProductCodeEditableCondition.class)
    @Column(unique=true)
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

    @ManyToOne
    @FormField(DisplayRank = 10)
    public ProductCategory getCategory() {
        return getValue("category");
    }

    public void setCategory(ProductCategory category) {
        setValue("category", category);
    }

    @ManyToOne
    @FormField(DisplayRank = 15)
    public Supplier getSupplier(){
        return getValue("supplier");
    }

    public void setSupplier(Supplier supplier) {
        setValue("supplier", supplier);
    }

    @FormField(DisplayRank = 20)
    public BigDecimal getPurchasePrice() {
        return getValue("purchasePrice");
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        setValue("purchasePrice", purchasePrice);
    }

    @FormField(DisplayRank = 22)
    public BigDecimal getRetailPrice() {
        return getValue("retailPrice");
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        setValue("retailPrice", retailPrice);
    }

    @FormField(DisplayRank = 23)
    @Transient
    public String getGrossProfitRateString() {
        if (getPurchasePrice() != null && getRetailPrice() != null) {
            final BigDecimal bd1 = getRetailPrice().subtract(getPurchasePrice());
            final BigDecimal bd2 = bd1.divide(getRetailPrice(), 4, BigDecimal.ROUND_HALF_UP);
            final BigDecimal bd3 = bd2.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            return String.valueOf(bd3) + "%";
        }
        return "-";
    }

    @FormField(DisplayRank = 25)
    public String getLink() {
        return getValue("link");
    }

    public void setLink(String link) {
        setValue("link", link);
    }

    @FormField(DisplayRank = 30)
    public String getDistinguishingFeature(){
        return getValue("distinguishingFeature");
    }

    public void setDistinguishingFeature(String df) {
        setValue("distinguishingFeature", df);
    }

    @FormField(DisplayRank = 35)
    public Integer getLeadDay() {
        return getValue("leadDay");
    }

    public void setLeadDay(Integer leadDay) {
        setValue("leadDay", leadDay);
    }

    @FormField(DisplayRank = 40)
    public Integer getDeliverDay() {
        return getValue("deliverDay");
    }

    public void setDeliverDay(Integer deliverDay) {
        setValue("deliverDay", deliverDay);
    }

}
