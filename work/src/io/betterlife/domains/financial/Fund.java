package io.betterlife.domains.financial;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.security.User;
import io.betterlife.rest.Form;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
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
    @Form(RepresentField="name")
    public FundCategory getFundCategory() {
        return getValue("fundCategory");
    }

    public void setCostCenter(CostCenter costCenter) {
        setValue("costCenter", costCenter);
    }

    @ManyToOne
    @Form(RepresentField="name")
    public CostCenter getCostCenter() {
        return getValue("costCenter");
    }

    public void setUser(User user) {
        setValue("user", user);
    }

    @ManyToOne
    @Form(RepresentField="displayName")
    public User getUser() {
        return getValue("user");
    }

    public void setAmount(BigDecimal amount) {
        setValue("amount", amount);
    }

    public BigDecimal getAmount() {
        return getValue("amount");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    public String getRemark() {
        return getValue("remark");
    }

    public void setDate(Date date) {
        setValue("date", date);
    }

    public Date getDate() {
        return getValue("date");
    }

    @Enumerated(EnumType.STRING)
    public FundType getFundType() {
        return getValue("fundType");
    }

    public void setFundType(FundType fundType) {
        setValue("fundType", fundType);
    }
}
