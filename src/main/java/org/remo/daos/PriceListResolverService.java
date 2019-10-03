package org.remo.daos;


import org.remo.JPAUtil;
import org.remo.models.Product;
import org.remo.models.ProductGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;


/**
 * Questo service è usato esclusivamente per risolvere il prezzo da assegnare
 * al servizio.
 *
 */
public class PriceListResolverService {

    private EntityManagerFactory emf =  JPAUtil.getEntityManagerFactory();

    private EntityManager em;

    public PriceListResolverService() {
        em = emf.createEntityManager();
    }


    /**
     * Cerca il prezzo per GruppoProdotti-prodotto e se non lo trova
     * cerca il prezzo per prodotto.
     *
     * @param product
     * @param productGroup
     * @return
     */
    public BigDecimal getProductPrice(Product product, ProductGroup productGroup) {
        BigDecimal result = null;
        TypedQuery<BigDecimal> qryByProdAndProductGroup =
                em.createQuery("Select p.price from ProductProductGroup p " +
                        "where p.product=:product and p.productgroup=:productGroup", BigDecimal.class);
        qryByProdAndProductGroup.setParameter("product", product);
        qryByProdAndProductGroup.setParameter("productGroup", productGroup);
        result = qryByProdAndProductGroup.getSingleResult();

        if (result==null) {
            TypedQuery<BigDecimal> qryByProd =
                    em.createQuery("Select p.price from Product p where p.product=:product", BigDecimal.class);
            qryByProd.setParameter("product", product);
            result = qryByProdAndProductGroup.getSingleResult();
        }

        return result;
    }


    /**
     * Cerca il prezzo in base al codice del prodotto e del gruppo prodotti e se non trova riscontro
     * prende il prezzo del prodotto.
     *
     * @param productCode
     * @param productGroupCode
     * @return
     */
    public BigDecimal getProductPrice(String productCode, String productGroupCode) {
        BigDecimal result;

        try {
            TypedQuery<BigDecimal> qryByProdAndProductGroup =
                    em.createQuery("Select p.price from ProductProductGroup p Join p.product pr Join p.productgroup pg " +
                            "where pr.code=:productCode and pg.code=:productGroupCode", BigDecimal.class);
            qryByProdAndProductGroup.setParameter("productCode", productCode);
            qryByProdAndProductGroup.setParameter("productGroupCode", productGroupCode);
            result = qryByProdAndProductGroup.getSingleResult();
        } catch(NoResultException e) {
            result = null;
        }

        if (result==null) {
            TypedQuery<BigDecimal> qryByProd =
                    em.createQuery("Select p.price from Product p where p.code=:productCode", BigDecimal.class);
            qryByProd.setParameter("productCode", productCode);
            result = qryByProd.getSingleResult();
        }

        return result;
    }
}
