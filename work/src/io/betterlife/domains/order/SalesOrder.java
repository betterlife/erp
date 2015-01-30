package io.betterlife.domains.order;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.catalog.Product;
import io.betterlife.domains.common.Supplier;
import io.betterlife.domains.security.User;
import io.betterlife.rest.Form;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SalesOrder.getById", query = "SELECT c FROM SalesOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "SalesOrder.getAll",  query = "SELECT c FROM SalesOrder c WHERE c.active = TRUE")
})
public class SalesOrder extends BaseObject {

    @ManyToOne
    @Form(DisplayRank = 5, RepresentField = "name")
    public Product getProduct() {
        return getValue("product");
    }

    public void setProduct(Product product) {
        setValue("product", product);
    }

    @Transient
    @Form(DisplayRank = 10, RepresentField = "name")
    public Supplier getSupplier() {
        return getProduct().getSupplier();
    }

    @ManyToOne
    @Form(DisplayRank = 15, RepresentField = "displayName")
    public User getTransactor() {
        return getValue("transactor");
    }

    public void setTransactor(User transactor) {
        setValue("transactor", transactor);
    }

    @Form(DisplayRank = 20)
    public void setQuantity(BigDecimal quantity) {
        setValue("quantity", quantity);
    }

    public BigDecimal getQuantity() {
        return getValue("quantity");
    }

    @Form(DisplayRank = 25)
    public void setPricePerUnit(BigDecimal ppu) {
        setValue("pricePerUnit", ppu);
    }

    public BigDecimal getPricePerUnit() {
        return getValue("pricePerUnit");
    }

    @Form(DisplayRank = 30)
    public void setLogisticAmount(BigDecimal logisticAmount) {
        setValue("logisticAmount", logisticAmount);
    }

    public BigDecimal getLogisticAmount() {
        return getValue("logisticAmount");
    }

    @Transient
    @Form(DisplayRank = 35)
    public BigDecimal getAmount() {
        return getPricePerUnit().multiply(getQuantity());
    }

    @Transient
    @Form(DisplayRank = 40)
    public BigDecimal getUnitLogisticAmount() {
        return getLogisticAmount().divide(getQuantity(), 2, BigDecimal.ROUND_HALF_UP);
    }

    @Form(DisplayRank = 45)
    public Date getOrderDate() {
        return getValue("orderDate");
    }

    public void setOrderDate(Date date) {
        setValue("orderDate", date);
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @Form(DisplayRank = 50)
    public String getRemark() {
        return getValue("remark");
    }
}
