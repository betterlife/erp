package io.betterlife.erp.domains.financial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.betterlife.erp.enums.IncomingStatus;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.order.SalesOrder;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.condition.FalseCondition;
import io.betterlife.framework.domains.security.User;

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
    @NamedQuery(name = "Incoming.getById", query = "SELECT e FROM Incoming e WHERE e.id = :id AND e.active = TRUE"),
    @NamedQuery(name = "Incoming.getAll",  query = "SELECT e FROM Incoming e WHERE e.active = TRUE ORDER BY e.id DESC")
})
public class Incoming extends BaseObject {

    public void setIncomingCategory(IncomingCategory incomingCategory) {
        setValue("incomingCategory", incomingCategory);
    }

    @ManyToOne
    @FormField(RepresentField="name", DisplayRank =10)
    public IncomingCategory getIncomingCategory() {
        return getValue("incomingCategory");
    }

    @ManyToOne
    @FormField(DisplayRank = 1, RepresentField="displayName")
    public User getPayee() {
        return getValue("payee");
    }

    public void setPayee(User payee) {
        setValue("payee", payee);
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
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy/MM/dd", timezone="CST")
    @Temporal(value= TemporalType.DATE)
    public Date getDate() {
        return getValue("date");
    }

    @FormField(DisplayRank = 24)
    public IncomingStatus getIncomingStatus() {
        return getValue("incomingStatus");
    }

    public void setIncomingStatus(IncomingStatus incomingStatus) {
        setValue("incomingStatus", incomingStatus);
    }

    @OneToOne(fetch=FetchType.LAZY)
    @FormField(DisplayRank = 20, RepresentField = "representField")
    @JsonManagedReference
    public SalesOrder getSalesOrder() {
        return getValue("salesOrder");
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        setValue("salesOrder", salesOrder);
    }

    @FormField(DisplayRank = 25)
    public String getRemark() {
        return getValue("remark");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }
}
