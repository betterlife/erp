package io.betterlife.domains.finical;

import io.betterlife.domains.BaseObject;
import io.betterlife.domains.security.User;

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
    @NamedQuery(name = "getExpenseById", query = "SELECT e FROM Expense e WHERE e.id = :id ")
})

public class Expense extends BaseObject {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    long id;

    @ManyToOne
    private ExpenseCategory category;

    @ManyToOne
    private CostCenter costCenter;

    @ManyToOne
    private User user;
    private BigDecimal amount;
    private String remark;
    private Date date;

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setExpenseDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public long getId(){
        return id;
    }

}
