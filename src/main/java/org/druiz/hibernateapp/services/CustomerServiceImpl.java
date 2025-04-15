package org.druiz.hibernateapp.services;

import jakarta.persistence.EntityManager;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.repositories.CrudRepository;
import org.druiz.hibernateapp.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private EntityManager entityManager;
    private CrudRepository<Customer> repository;

    public CustomerServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.repository = new CustomerRepository(entityManager);
    }

    @Override
    public List<Customer> getAll() {
        return this.repository.getAll();
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        return Optional.ofNullable(this.repository.getById(id));
    }

    @Override
    public void save(Customer customer) {
        try {
            this.entityManager.getTransaction().begin();

            this.repository.save(customer);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void delete(Customer customer) {
        try {
            this.entityManager.getTransaction().begin();

            this.repository.delete(customer);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            e.printStackTrace(System.out);
        }
    }
}
