package io.betterlife.domains.common;

import io.betterlife.domains.BaseObject;
import io.betterlife.rest.Form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Supplier.getById", query = "SELECT c FROM Supplier c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "Supplier.getAll", query = "SELECT c FROM Supplier c WHERE c.active = TRUE")
})
public class Supplier extends BaseObject {

    @Form(DisplayRank = 5)
    public String getName() {
        return getValue("name");
    }

    public void setName(String name) {
        setValue("name", name);
    }

    public void setContact(String contact) {
        setValue("contact", contact);
    }

    @Form(DisplayRank = 10)
    public String getContact() {
        return getValue("contact");
    }

    @Form(DisplayRank = 15)
    public String getEmail() {
        return getValue("email");
    }

    public void setEmail(String email) {
        setValue("email", email);
    }

    @Form(DisplayRank = 20)
    public String getPhone() {
        return getValue("phone");
    }

    public void setPhone(String phone) {
        setValue("phone", phone);
    }

    @Form(DisplayRank = 25)
    public String getQq() {
        return getValue("qq");
    }

    public void setQq(String qq) {
        setValue("qq", qq);
    }

    @Form(DisplayRank = 30)
    public String getWebsite() {
        return getValue("website");
    }

    public void setWebsite(String website) {
        setValue("website", website);
    }

    @Form(DisplayRank = 35)
    public String getWholesaleRequirement() {
        return getValue("wholesaleRequirement");
    }

    public void setWholesaleRequirement(String wholesaleRequirement) {
        setValue("wholesaleRequirement", wholesaleRequirement);
    }

    @Form(DisplayRank = 40)
    public boolean getCanMixedWholesale() {
        return getValue("canMixedWholesale");
    }

    public void setCanMixedWholesale(boolean canMixedWholesale) {
        setValue("canMixedWholesale", canMixedWholesale);
    }

    @Form(DisplayRank = 45)
    @Column(length = 512)
    public String getRemark() {
        return getValue("remark");
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

}
