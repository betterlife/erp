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
    @NamedQuery(name = "SalesOrder.getById", query = "SELECT c FROM SalesOrder c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "SalesOrder.getAll",  query = "SELECT c FROM SalesOrder c WHERE c.active = TRUE")
})
public class SalesOrder extends BaseObject {
}
