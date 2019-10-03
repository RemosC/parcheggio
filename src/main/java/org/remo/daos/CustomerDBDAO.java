package org.remo.daos;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.remo.JPAUtil;
import org.remo.dtos.CustomerDTO;
import org.remo.models.Customer;
import org.remo.models.Register;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CustomerDBDAO implements CustomerDAO {

    private EntityManagerFactory emf =  JPAUtil.getEntityManagerFactory();

    private EntityManager em;

    public CustomerDBDAO() {
        em = emf.createEntityManager();
    }


    /**
     * Registra il cliente nel db.
     * - se il cliente è nuovo lo inserisce
     * - se il cliente è già presente lo aggiorna.
     *
     * @param customerDto
     * @return Customer
     */
    @Override
    public Customer save(CustomerDTO customerDto) {
        em.getTransaction().begin();

        Customer customer;
        // verifica l'esistenza del cliente tramite il suo ssn
        List<Customer> customers = getCustomersBySSN(customerDto.getSsn());
        if (customers.isEmpty())
            customer = new Customer();
        else
            customer = customers.get(0);

        customer.setFirstname(customerDto.getFirstname());
        customer.setLastname(customerDto.getLastname());
        customer.setEmail(customerDto.getEmail());
        customer.setSsn(customerDto.getSsn());
        customer.setObsolete(customerDto.isObsolete());

        em.persist(customer);

        em.getTransaction().commit();
        return customer;
    }


    /**
     * Cerca il cliente per SSN e ritorna un elenco di clienti con lo stesso CF.
     * L'SSN può essere anche parziale.
     *
     * @param ssn
     * @return
     */
    @Override
    public List<Customer> getCustomersBySSN(String ssn) {
        TypedQuery<Customer> query = em.createQuery("Select c from Customer c where c.ssn like concat('%',:ssn,'%')", Customer.class);
        query.setParameter("ssn", ssn);
        return query.getResultList();
    }

    @Override
    public List<Customer> getCustomersByEmail(String email) {
        TypedQuery<Customer> query = em.createQuery("Select c from Customer c where c.ssn like concat('%',:email,'%')", Customer.class);
        query.setParameter("email", email);
        return query.getResultList();
    }


    @Override
    public void remove(CustomerDTO customerDto) {

    }
}
