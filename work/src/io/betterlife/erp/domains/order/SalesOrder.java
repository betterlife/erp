package io.betterlife.erp.domains.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.catalog.Product;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.Incoming;
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
    @NamedQuery(name = "SalesOrder.getById", query = "SELECT c FROM SalesOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "SalesOrder.getAll", query = "SELECT c FROM SalesOrder c WHERE c.active = TRUE")
})
public class SalesOrder extends BaseObject {

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

    @ManyToOne
    @FormField(DisplayRank = 15, RepresentField = "displayName")
    public User getTransactor() {
        return getValue("transactor");
    }

    public void setTransactor(User transactor) {
        setValue("transactor", transactor);
    }

    public void setQuantity(BigDecimal quantity) {
        setValue("quantity", quantity);
    }

    @FormField(DisplayRank = 20)
    public BigDecimal getQuantity() {
        return getValue("quantity");
    }

    public void setPricePerUnit(BigDecimal ppu) {
        setValue("pricePerUnit", ppu);
    }

    @FormField(DisplayRank = 25)
    public BigDecimal getPricePerUnit() {
        return getValue("pricePerUnit");
    }

    public void setLogisticAmount(BigDecimal logisticAmount) {
        setValue("logisticAmount", logisticAmount);
    }

    @FormField(DisplayRank = 30)
    public BigDecimal getLogisticAmount() {
        return getValue("logisticAmount");
    }

    @Transient
    @FormField(DisplayRank = 35)
    public BigDecimal getAmount() {
        return getPricePerUnit().multiply(getQuantity());
    }

    @Transient
    @FormField(DisplayRank = 40)
    public BigDecimal getUnitLogisticAmount() {
        BigDecimal result = BigDecimal.ZERO;
        if (getLogisticAmount() != null && getQuantity() != null) {
            return getLogisticAmount().divide(getQuantity(), 2, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    @FormField(DisplayRank = 45)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    @Temporal(value = TemporalType.DATE)
    public Date getOrderDate() {
        return getValue("orderDate");
    }

    public void setOrderDate(Date date) {
        setValue("orderDate", date);
    }

    public void setStockOutDate(Date date) {
        setValue("stockOutDate", date);
    }

    @FormField(DisplayRank = 47)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    @Temporal(value = TemporalType.DATE)
    public Date getStockOutDate() {
        return getValue("stockOutDate");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @FormField(DisplayRank = 50)
    public String getRemark() {
        return getValue("remark");
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "salesOrder")
    @FormField(Visible = FalseCondition.class)
    public Incoming getIncoming() {
        return getValue("incoming");
    }

    public void setIncoming(Incoming incoming) {
        setValue("incoming", incoming);
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "salesOrder")
    @FormField(Visible = FalseCondition.class)
    @JsonBackReference
    public List<Expense> getExpenses() {
        return getValue("expenses");
    }

    public void setExpenses(List<Expense> expenses) {
        setValue("expenses", expenses);
    }

}
