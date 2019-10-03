package org.remo.daos;


import org.remo.dtos.CustomerDTO;
import org.remo.models.Customer;

import java.util.List;

public interface CustomerDAO {
    Customer save(CustomerDTO customerDto);
    List<Customer> getCustomersBySSN(String ssn);
    List<Customer> getCustomersByEmail(String email);

    void remove(CustomerDTO customerDto);
}
