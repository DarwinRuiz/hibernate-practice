package org.druiz.hibernateapp.repositories;

import jakarta.persistence.EntityManager;
import org.druiz.hibernateapp.entities.Customer;

import java.util.List;

public class CustomerRepository implements CrudRepository<Customer> {

    private EntityManager entityManager;

    public CustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> getAll() {
        return this.entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer getById(Integer id) {
        return this.entityManager.find(Customer.class, id);
    }

    @Override
    public void save(Customer customer) {
        if (customer.getCustomerId() != null && customer.getCustomerId() > 0) {
            this.entityManager.persist(customer);
        } else {
            this.entityManager.merge(customer);
        }
    }

    @Override
    public void delete(Customer customer) {
        this.entityManager.remove(customer);
    }
}
