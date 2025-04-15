package org.druiz.hibernateapp.services;

import org.druiz.hibernateapp.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAll();

    Optional<Customer> getById(Integer id);

    void save(Customer customer);

    void delete(Customer customer);
}
