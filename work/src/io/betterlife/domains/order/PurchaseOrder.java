package io.betterlife.domains.order;

import io.betterlife.domains.BaseObject;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Author: Lawrence Liu
 * Date: 1/7/15
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PurchaseOrder.getById", query = "SELECT c FROM PurchaseOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "PurchaseOrder.getAll",  query = "SELECT c FROM PurchaseOrder c WHERE c.active = TRUE")
})
public class PurchaseOrder extends BaseObject {
}
