package io.betterlife.erp.domains.financial;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.erp.domains.order.SalesOrder;
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
    @NamedQuery(name = "Incoming.getById", query = "SELECT e FROM Incoming e WHERE e.id = :id AND e.active = TRUE"),
    @NamedQuery(name = "Incoming.getAll",  query = "SELECT e FROM Incoming e WHERE e.active = TRUE")
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

    public void setAmount(BigDecimal amount) {
        setValue("amount", amount);
    }

    @FormField(DisplayRank = 3)
    public BigDecimal getAmount() {
        return getValue("amount");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @FormField(DisplayRank = 20)
    public String getRemark() {
        return getValue("remark");
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

    @OneToOne(fetch=FetchType.LAZY)
    @FormField(Visible = FalseCondition.class)
    public SalesOrder getSalesOrder() {
        return getValue("salesOrder");
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        setValue("salesOrder", salesOrder);
    }
}
