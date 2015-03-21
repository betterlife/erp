package io.betterlife.erp.domains.financial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.order.PurchaseOrder;
import io.betterlife.erp.domains.order.SalesOrder;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.condition.FalseCondition;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 * This is the expense Entity definition
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Expense.getById", query = "SELECT e FROM Expense e WHERE e.id = :id AND e.active = TRUE"),
    @NamedQuery(name = "Expense.getAll", query = "SELECT e FROM Expense e WHERE e.active = TRUE")
})
public class Expense extends BaseObject {

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        setValue("expenseCategory", expenseCategory);
    }

    @ManyToOne
    @FormField(RepresentField = "name", DisplayRank = 10)
    public ExpenseCategory getExpenseCategory() {
        return getValue("expenseCategory");
    }

    public void setCostCenter(CostCenter costCenter) {
        setValue("costCenter", costCenter);
    }

    @ManyToOne
    @FormField(RepresentField = "name", DisplayRank = 15)
    public CostCenter getCostCenter() {
        return getValue("costCenter");
    }

    public void setUser(User user) {
        setValue("user", user);
    }

    @ManyToOne
    @FormField(RepresentField = "displayName", DisplayRank = 4)
    public User getUser() {
        return getValue("user");
    }

    public void setAmount(BigDecimal amount) {
        setValue("amount", amount);
    }

    @FormField(DisplayRank = 3)
    public BigDecimal getAmount() {
        return getValue("amount");
    }

    public void setDate(Date date) {
        setValue("date", date);
    }

    @FormField(DisplayRank = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    @Temporal(value = TemporalType.DATE)
    public Date getDate() {
        return getValue("date");
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @FormField(RepresentField = "representField", DisplayRank = 25)
    @JsonManagedReference
    public PurchaseOrder getPurchaseOrder() {
        return getValue("purchaseOrder");
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        setValue("purchaseOrder", purchaseOrder);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @FormField(RepresentField = "representField", DisplayRank = 30)
    @JsonManagedReference
    public SalesOrder getSalesOrder() {
        return getValue("salesOrder");
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        setValue("salesOrder", salesOrder);
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @FormField(DisplayRank = 35)
    public String getRemark() {
        return getValue("remark");
    }

    @FormField(DisplayRank = 40, TrueLabel = "hasInvoice", FalseLabel = "noInvoice")
    public boolean getHasInvoice() {
        return getValue("hasInvoice");
    }

    public void setHasInvoice(boolean hasInvoice) {
        setValue("hasInvoice", hasInvoice);
    }
}
