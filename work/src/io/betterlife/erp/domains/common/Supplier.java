package io.betterlife.erp.domains.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.betterlife.erp.domains.financial.Expense;
import io.betterlife.erp.domains.financial.PaymentMethod;
import io.betterlife.framework.annotation.EntityForm;
import io.betterlife.framework.condition.FalseCondition;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.annotation.FormField;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Supplier.getById", query = "SELECT c FROM Supplier c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "Supplier.getAll", query = "SELECT c FROM Supplier c WHERE c.active = TRUE ORDER BY c.id DESC"),
    @NamedQuery(name = "Supplier.getByKeyword", query = "SELECT c FROM Supplier c WHERE c.active = TRUE and (c.name like :keyword or c.contact like :keyword or c.email like :keyword or c.phone like :keyword or c.qq like :keyword or c.website like :keyword or c.remark like :keyword)")
})
@EntityForm(DetailField = "paymentMethods")
public class Supplier extends BaseObject {

    @FormField(DisplayRank = 5)
    public String getName() {
        return getValue("name");
    }

    public void setName(String name) {
        setValue("name", name);
    }

    public void setContact(String contact) {
        setValue("contact", contact);
    }

    @FormField(DisplayRank = 10)
    public String getContact() {
        return getValue("contact");
    }

    @FormField(DisplayRank = 15)
    public String getEmail() {
        return getValue("email");
    }

    public void setEmail(String email) {
        setValue("email", email);
    }

    @FormField(DisplayRank = 20)
    public String getPhone() {
        return getValue("phone");
    }

    public void setPhone(String phone) {
        setValue("phone", phone);
    }

    @FormField(DisplayRank = 25)
    public String getQq() {
        return getValue("qq");
    }

    public void setQq(String qq) {
        setValue("qq", qq);
    }

    @FormField(DisplayRank = 30)
    public String getWebsite() {
        return getValue("website");
    }

    public void setWebsite(String website) {
        setValue("website", website);
    }

    @FormField(DisplayRank = 35)
    public String getWholesaleRequirement() {
        return getValue("wholesaleRequirement");
    }

    public void setWholesaleRequirement(String wholesaleRequirement) {
        setValue("wholesaleRequirement", wholesaleRequirement);
    }

    @FormField(DisplayRank = 40, TrueLabel = "Can", FalseLabel = "Cannot")
    public boolean getCanMixedWholesale() {
        return getValue("canMixedWholesale");
    }

    public void setCanMixedWholesale(boolean canMixedWholesale) {
        setValue("canMixedWholesale", canMixedWholesale);
    }

    @FormField(DisplayRank = 45)
    @Column(length = 512)
    public String getRemark() {
        return getValue("remark");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "supplier")
    @FormField(Visible = FalseCondition.class)
    @JsonManagedReference
    public List<PaymentMethod> getPaymentMethods() {
        return getValue("paymentMethods");
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        setValue("paymentMethods", paymentMethods);
    }
}
