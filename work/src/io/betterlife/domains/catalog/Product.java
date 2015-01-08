package io.betterlife.domains.catalog;

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
    @NamedQuery(name = "Product.getById", query = "SELECT c FROM Product c WHERE c.id = :id AND c.active = TRUE"),
    @NamedQuery(name = "Product.getAll",  query = "SELECT c FROM Product c WHERE c.active = TRUE")
})
public class Product extends BaseObject {
}
