package io.betterlife.erp.domains.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.betterlife.erp.domains.catalog.Product;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.PaymentMethod;
import io.betterlife.erp.trigger.PurchaseOrderSaveTrigger;
import io.betterlife.framework.annotation.EntityForm;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.annotation.Triggers;
import io.betterlife.framework.condition.FalseCondition;
import io.betterlife.framework.condition.TrueCondition;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.domains.security.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PurchaseOrder.getById", query = "SELECT c FROM PurchaseOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "PurchaseOrder.getAll", query = "SELECT c FROM PurchaseOrder c WHERE c.active = TRUE ORDER BY c.id DESC")
})
@Triggers(Save = PurchaseOrderSaveTrigger.class)
@EntityForm(DetailField = "orderLines", DetailFieldType = PurchaseOrderLine.class)
public class PurchaseOrder extends BaseObject {

    @ManyToOne
    @FormField(DisplayRank = 10, RepresentField = "name")
    public Supplier getSupplier() {
        return getValue("supplier");
    }

    public void setSupplier(Supplier supplier) {
        setValue("supplier", supplier);
    }

    @Transient
    @FormField(DisplayRank = 25)
    public BigDecimal getAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        if (null != getOrderLines() && getOrderLines().size() > 0) {
            for (PurchaseOrderLine line : getOrderLines()) {
                amount = amount.add(line.getAmount());
            }
        }
        return amount;
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    @FormField(Visible = FalseCondition.class)
    @JsonBackReference
    public List<Expense> getExpenses() {
        return getValue("expenses");
    }

    public void setExpenses(List<Expense> expenses) {
        setValue("expenses", expenses);
    }

    @FormField(Visible = FalseCondition.class)
    @Transient
    public String getRepresentField() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getId()).append("-").append("-").append(getAmount());
        if (getOrderDate() != null){
            stringBuilder.append("-").append(new SimpleDateFormat("yyyy/MM/dd").format(getOrderDate()));
        }
        return stringBuilder.toString();
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "orderHeader")
    @FormField(Visible = FalseCondition.class)
    @JsonManagedReference
    public List<PurchaseOrderLine> getOrderLines() {
        return getValue("orderLines");
    }

    public void setOrderLines(List<PurchaseOrderLine> lines) {
        setValue("orderLines", lines);
    }
}
