package io.betterlife.erp.domains.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.betterlife.framework.annotation.EntityTriggers;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.catalog.Product;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.condition.FalseCondition;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PurchaseOrder.getById", query = "SELECT c FROM PurchaseOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "PurchaseOrder.getAll", query = "SELECT c FROM PurchaseOrder c WHERE c.active = TRUE")
})
@EntityTriggers()
public class PurchaseOrder extends BaseObject {

    @ManyToOne
    @FormField(DisplayRank = 5, RepresentField = "name")
    public Product getProduct() {
        return getValue("product");
    }

    public void setProduct(Product product) {
        setValue("product", product);
    }

    @Transient
    @FormField(DisplayRank = 10, RepresentField = "name")
    public Supplier getSupplier() {
        return getProduct().getSupplier();
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

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    @FormField(DisplayRank = 30)
    public Date getOrderDate() {
        return getValue("orderDate");
    }

    public void setOrderDate(Date date) {
        setValue("orderDate", date);
    }

    @FormField(DisplayRank = 30)
    public BigDecimal getLogisticAmount() {
        return getValue("logisticAmount");
    }

    public void setLogisticAmount(BigDecimal amount) {
        setValue("logisticAmount", amount);
    }

    @FormField(DisplayRank = 35)
    @Transient
    public BigDecimal getUnitLogisticAmount() {
        BigDecimal result = BigDecimal.ZERO;
        if (getLogisticAmount() != null && getQuantity() != null) {
            result = getLogisticAmount().divide(getQuantity(), 2, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    @FormField(DisplayRank = 40)
    public BigDecimal getOtherAmount() {
        return getValue("otherAmount");
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        setValue("otherAmount", otherAmount);
    }

    @FormField(DisplayRank = 42)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    @Temporal(value = TemporalType.DATE)
    public Date getStockInDate() {
        return getValue("stockInDate");
    }

    public void setStockInDate(Date date) {
        setValue("stockInDate", date);
    }

    @ManyToOne
    @FormField(DisplayRank = 45, RepresentField = "displayName")
    public User getUser() {
        return getValue("user");
    }

    public void setUser(User user) {
        setValue("user", user);
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @FormField(DisplayRank = 50)
    public String getRemark() {
        return getValue("remark");
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    @FormField(Visible = FalseCondition.class)
    public List<Expense> getExpenses() {
        return getValue("expenses");
    }

    public void setExpenses(List<Expense> expenses) {
        setValue("expenses", expenses);
    }
}
