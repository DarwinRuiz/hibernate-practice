package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

public class List {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        java.util.List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();

        customers.forEach(System.out::println);

        em.close();
    }
}
