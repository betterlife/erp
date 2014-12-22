package io.betterlife.domains.financial;

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
    @NamedQuery(name = "Fund.getById", query = "SELECT e FROM Fund e WHERE e.id = :id "),
    @NamedQuery(name = "Fund.getAll",  query = "SELECT e FROM Fund e")
})
public class Fund extends BaseObject {

    public void setFundCategory(FundCategory fundCategory) {
        setValue("fundCategory", fundCategory);
    }

    @ManyToOne
    @Form(RepresentField="name", DisplayRank =10)
    public FundCategory getFundCategory() {
        return getValue("fundCategory");
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
    public Date getDate() {
        return getValue("date");
    }

    @Enumerated(EnumType.STRING)
    @Form(DisplayRank = 1)
    public FundType getFundType() {
        return getValue("fundType");
    }

    public void setFundType(FundType fundType) {
        setValue("fundType", fundType);
    }
}
