package io.betterlife.erp.domains.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.betterlife.erp.domains.catalog.Product;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.domains.BaseObject;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author: Lawrence Liu
 * Date: 15/4/5
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PurchaseOrderLine.getById", query = "SELECT c FROM PurchaseOrderLine c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "PurchaseOrderLine.getAll", query = "SELECT c FROM PurchaseOrderLine c WHERE c.active = TRUE ORDER BY c.id DESC")
})
public class PurchaseOrderLine extends BaseObject {
    @ManyToOne
    @FormField(DisplayRank = 5, RepresentField = "name")
    public Product getProduct() {
        return getValue("product");
    }

    public void setProduct(Product product) {
        setValue("product", product);
    }

    @FormField(DisplayRank = 15)
    public BigDecimal getPricePerUnit() {
        return getValue("pricePerUnit");
    }

    public void setPricePerUnit(BigDecimal price) {
        setValue("pricePerUnit", price);
    }

    @FormField(DisplayRank = 20)
    public BigDecimal getQuantity() {
        return getValue("quantity");
    }

    public void setQuantity(BigDecimal quantity) {
        setValue("quantity", quantity);
    }

    @Transient
    @FormField(DisplayRank = 25)
    public BigDecimal getAmount() {
        return getQuantity().multiply(getPricePerUnit());
    }

    @ManyToOne
    @JsonBackReference
    public PurchaseOrder getOrderHeader(){
        return getValue("orderHeader");
    }

    public void setOrderHeader(PurchaseOrder orderHeader) {
        setValue("orderHeader", orderHeader);
    }
}
