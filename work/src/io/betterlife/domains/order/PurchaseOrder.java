package io.betterlife.domains.order;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @NamedQuery(name = "PurchaseOrder.getById", query = "SELECT c FROM PurchaseOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "PurchaseOrder.getAll", query = "SELECT c FROM PurchaseOrder c WHERE c.active = TRUE")
})
public class PurchaseOrder extends BaseObject {

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

    @Form(DisplayRank = 15)
    public BigDecimal getPricePerUnit() {
        return getValue("pricePerUnit");
    }

    public void setPricePerUnit(BigDecimal price) {
        setValue("pricePerUnit", price);
    }

    @Form(DisplayRank = 20)
    public BigDecimal getQuantity() {
        return getValue("quantity");
    }

    public void setQuantity(BigDecimal quantity) {
        setValue("quantity", quantity);
    }

    @Transient
    @Form(DisplayRank = 25)
    public BigDecimal getAmount() {
        return getQuantity().multiply(getPricePerUnit());
    }

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    @Form(DisplayRank = 30)
    public Date getOrderDate() {
        return getValue("orderDate");
    }

    public void setOrderDate(Date date) {
        setValue("orderDate", date);
    }

    @Form(DisplayRank = 30)
    public BigDecimal getLogisticAmount() {
        return getValue("logisticAmount");
    }

    public void setLogisticAmount(BigDecimal amount) {
        setValue("logisticAmount", amount);
    }

    @Form(DisplayRank = 35)
    @Transient
    public BigDecimal getUnitLogisticAmount() {
        BigDecimal result = BigDecimal.ZERO;
        if (getLogisticAmount() != null && getQuantity() != null) {
            result = getLogisticAmount().divide(getQuantity(), 2, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    @Form(DisplayRank = 40)
    public BigDecimal getOtherAmount() {
        return getValue("otherAmount");
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        setValue("otherAmount", otherAmount);
    }

    @Form(DisplayRank = 42)
    @Temporal(value=TemporalType.DATE)
    public Date getStockInDate(){
        return getValue("stockInDate");
    }

    public void setStockInDate(Date date){
        setValue("stockInDate", date);
    }

    @ManyToOne
    @Form(DisplayRank = 45, RepresentField = "displayName")
    public User getUser() {
        return getValue("user");
    }

    public void setUser(User user) {
        setValue("user", user);
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @Form(DisplayRank = 50)
    public String getRemark() {
        return getValue("remark");
    }
}
