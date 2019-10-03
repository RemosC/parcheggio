package org.remo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="productgroups")
public class ProductGroup extends BaseModel {

    @Basic
    private String code;

    @Basic
    private String description;

    // One to many verso la tabella di mezzo.
    @OneToMany(mappedBy = "productgroup", cascade = CascadeType.ALL)
    private Set<ProductProductGroup> products;

    public ProductGroup() {}

    public ProductGroup(String code, String description, Set<ProductProductGroup> products) {
        this.code = code;
        this.description = description;
        this.products = products;
        if (this.products==null)
            this.products = new HashSet<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductProductGroup> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductProductGroup> products) {
        this.products = products;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
