package org.remo.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


/**
 * definisce il prodotto ed un suo prezzo base
 *
 */
@Entity
@Table(name="products")
public class Product extends BaseModel{

    private String code;

    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductProductGroup> productGroups;

    @Basic
    private BigDecimal price;

    public Product() {}

    public Product(String code, String description, Set<ProductProductGroup> productGroups, BigDecimal price) {
        this.code = code;
        this.description = description;
        this.productGroups = productGroups;
        if (this.productGroups==null)
            this.productGroups = new HashSet<>();
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(Set<ProductProductGroup> productProductGroups) {
        this.productGroups = productProductGroups;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
