package io.betterlife.erp.domains.financial;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.betterlife.erp.domains.common.Supplier;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.domains.BaseObject;

import javax.persistence.*;

/**
 * Author: Lawrence Liu
 * Date: 15/3/9
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PaymentMethod.getById", query = "SELECT p FROM PaymentMethod p WHERE p.id = :id AND p.active = TRUE"),
    @NamedQuery(name = "PaymentMethod.getAll", query = "SELECT p FROM PaymentMethod p WHERE p.active = TRUE")
})
public class PaymentMethod extends BaseObject {

    @ManyToOne(fetch = FetchType.LAZY)
    @FormField(RepresentField = "name", DisplayRank = 3)
    @JsonBackReference
    public Supplier getSupplier(){
        return getValue("supplier");
    }

    public void setSupplier(Supplier supplier) {
        setValue("supplier", supplier);
    }

    @FormField(DisplayRank = 5)
    public String getAccountName() {
        return getValue("accountName");
    }

    public void setAccountName(String name) {
        setValue("accountName", name);
    }

    @FormField(DisplayRank = 10)
    public String getBankName() {
        return getValue("bankName");
    }

    public void setBankName(String bankName){
        setValue("bankName", bankName);
    }

    @FormField(DisplayRank = 15)
    public String getOpenAccountBankBranch(){
        return getValue("openAccountBankBranch");
    }

    public void setOpenAccountBankBranch(String branch) {
        setValue("openAccountBankBranch", branch);
    }

    @FormField(DisplayRank = 20)
    public String getAccountNumber() {
        return getValue("accountNumber");
    }

    public void setAccountNumber(String number) {
        setValue("accountNumber", number);
    }

    public void setRemark(String remark) {
        setValue("remark", remark);
    }

    @FormField(DisplayRank = 25)
    public String getRemark() {
        return getValue("remark");
    }

}
