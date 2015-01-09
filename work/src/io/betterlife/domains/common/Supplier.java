package io.betterlife.domains.common;

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
    @NamedQuery(name = "Supplier.getById", query = "SELECT c FROM Supplier c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "Supplier.getAll",  query = "SELECT c FROM Supplier c WHERE c.active = TRUE")
})
public class Supplier extends BaseObject{
}
