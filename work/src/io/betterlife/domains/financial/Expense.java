package io.betterlife.domains.financial;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.betterlife.domains.BaseObject;
import io.betterlife.domains.order.PurchaseOrder;
import io.betterlife.domains.security.User;
import io.betterlife.rest.Form;
import io.betterlife.util.condition.FalseCondition;

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
    @NamedQuery(name = "Expense.getAll",  query = "SELECT e FROM Expense e WHERE e.active = TRUE")
})
public class Expense extends BaseObject {

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        setValue("expenseCategory", expenseCategory);
    }

    @ManyToOne
    @Form(RepresentField="name", DisplayRank =10)
    public ExpenseCategory getExpenseCategory() {
        return getValue("expenseCategory");
    }

    public void setCostCenter(CostCenter costCenter) {
        setValue("costCenter", costCenter);
    }

    @ManyToOne
    @Form(RepresentField="name", DisplayRank =15)
    public CostCenter getCostCenter() {
        return getValue("costCenter");
    }

    public void setUser(User user) {
        setValue("user", user);
    }

    @ManyToOne
    @Form(RepresentField="displayName", DisplayRank =4)
    public User getUser() {
        return getValue("user");
    }

    public void setAmount(BigDecimal amount) {
        setValue("amount", amount);
    }

    @Form(DisplayRank = 3)
    public BigDecimal getAmount() {
        return getValue("amount");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @Form(DisplayRank = 20)
    public String getRemark() {
        return getValue("remark");
    }

    public void setDate(Date date) {
        setValue("date", date);
    }

    @Form(DisplayRank = 2)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy/MM/dd", timezone="CST")
    @Temporal(value=TemporalType.DATE)
    public Date getDate() {
        return getValue("date");
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @Form(Visible = FalseCondition.class)
    public PurchaseOrder getPurchaseOrder() {
        return getValue("purchaseOrder");
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        setValue("purchaseOrder", purchaseOrder);
    }
}
