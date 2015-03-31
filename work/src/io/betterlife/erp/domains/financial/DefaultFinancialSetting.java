package io.betterlife.erp.domains.financial;

import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.domains.BaseObject;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Author: Lawrence Liu
 * Date: 15/2/8
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "DefaultFinancialSetting.getById", query = "SELECT e FROM DefaultFinancialSetting e WHERE e.id = :id AND e.active = TRUE"),
    @NamedQuery(name = "DefaultFinancialSetting.getAll",  query = "SELECT e FROM DefaultFinancialSetting e WHERE e.active = TRUE ORDER BY e.id DESC")
})
public class DefaultFinancialSetting extends BaseObject {

    @ManyToOne
    @FormField(DisplayRank = 5, RepresentField = "name")
    public ExpenseCategory getDefPOProductExpCate() {
        return getValue("defPOProductExpCate");
    }

    public void setDefPOProductExpCate(ExpenseCategory category) {
        setValue("defPOProductExpCate", category);
    }

    @ManyToOne
    @FormField(DisplayRank = 10, RepresentField = "name")
    public ExpenseCategory getDefPOLogisticExpCate() {
        return getValue("defPOLogisticExpCate");
    }

    public void setDefPOLogisticExpCate(ExpenseCategory category) {
        setValue("defPOLogisticExpCate", category);
    }

    @ManyToOne
    @FormField(DisplayRank = 15, RepresentField = "name")
    public ExpenseCategory getDefPOOtherExpCate() {
        return getValue("defPOOtherExpCate");
    }

    public void setDefPOOtherExpCate(ExpenseCategory category) {
        setValue("defPOOtherExpCate", category);
    }

    @ManyToOne
    @FormField(DisplayRank = 20, RepresentField = "name")
    public IncomingCategory getDefSOIncomingCate() {
        return getValue("defSOIncomingCate");
    }

    public void setDefSOIncomingCate(IncomingCategory category) {
        setValue("defSOIncomingCate", category);
    }

    @ManyToOne
    @FormField(DisplayRank = 25, RepresentField = "name")
    public ExpenseCategory getDefSOLogisticExpCate() {
        return getValue("defSOLogisticExpCate");
    }

    public void setDefSOLogisticExpCate(ExpenseCategory category) {
        setValue("defSOLogisticExpCate", category);
    }
}
