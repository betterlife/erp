package io.betterlife.domains.catalog;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.common.Supplier;
import io.betterlife.rest.Form;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.URL;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Product.getById", query = "SELECT c FROM Product c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "Product.getAll",  query = "SELECT c FROM Product c WHERE c.active = TRUE")
})
public class Product extends BaseObject {

    @Form(DisplayRank = 1)
    @Column(unique=true)
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

    @ManyToOne
    @Form(DisplayRank = 10)
    public ProductCategory getCategory() {
        return getValue("category");
    }

    public void setCategory(ProductCategory category) {
        setValue("category", category);
    }

    @ManyToOne
    @Form(DisplayRank = 15)
    public Supplier getSupplier(){
        return getValue("supplier");
    }

    public void setSupplier(Supplier supplier) {
        setValue("supplier", supplier);
    }

    @Form(DisplayRank = 20)
    public BigDecimal getPurchasePrice() {
        return getValue("purchasePrice");
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        setValue("purchasePrice", purchasePrice);
    }

    @Form(DisplayRank = 24)
    public BigDecimal getRetailPrice() {
        return getValue("retailPrice");
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        setValue("retailPrice", retailPrice);
    }

    @Transient
    public BigDecimal getGrossProfitRate() {
        if (getPurchasePrice() != null && getRetailPrice() != null) {
            return getPurchasePrice().divide(getRetailPrice(), BigDecimal.ROUND_HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Form(DisplayRank = 25)
    public String getLink() {
        return getValue("link");
    }

    public void setLink(String link) {
        setValue("link", link);
    }

    @Form(DisplayRank = 30)
    public String getDistinguishingFeature(){
        return getValue("distinguishingFeature");
    }

    public void setDistinguishingFeature(String df) {
        setValue("distinguishingFeature", df);
    }

    @Form(DisplayRank = 35)
    public int getLeadDay() {
        return getValue("leadDay");
    }

    public void setLeadDay(int leadDay) {
        setValue("leadDay", leadDay);
    }

    @Form(DisplayRank = 40)
    public int getDeliverDay() {
        return getValue("deliverDay");
    }

    public void setDeliverDay(int deliverDay) {
        setValue("deliverDay", deliverDay);
    }

}
