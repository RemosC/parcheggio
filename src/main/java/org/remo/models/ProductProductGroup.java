package org.remo.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * Tabella di mezzo per la relazione ManyToMany.
 * Associa un prezzo differente in funzione della categoria in cui è associato il prodotto
 *
 * definisce una chiave primaria composta: Product + ProductGroup
 * è possibile definire un prezzo speciale in base alla relazione ProductGroup-Product.
 *
 */
@Entity
@Table(name="products_pricelists")
public class ProductProductGroup implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name="productgroup_id")
    private ProductGroup productgroup;


    // attributo aggiuntivo alla relazione
    @Column(name="price")
    private BigDecimal price;

    public ProductProductGroup(){}

    public ProductProductGroup(Product product, ProductGroup productgroup, BigDecimal price) {
        this.product = product;
        this.productgroup = productgroup;
        this.price = price;

        product.getProductGroups().add(this);
        productgroup.getProducts().add(this);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductGroup getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(ProductGroup productGroup) {
        this.productgroup = productGroup;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductProductGroup that = (ProductProductGroup) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(productgroup, that.productgroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, productgroup);
    }
}
