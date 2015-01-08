package io.betterlife.domains.financial;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.betterlife.domains.BaseObject;
import io.betterlife.domains.security.User;
import io.betterlife.rest.Form;

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
    @Form(RepresentField="name", DisplayRank =10)
    public IncomingCategory getIncomingCategory() {
        return getValue("incomingCategory");
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
    @Temporal(value= TemporalType.DATE)
    public Date getDate() {
        return getValue("date");
    }

}
