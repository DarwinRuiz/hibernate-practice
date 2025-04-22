package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HibernateCriteriaDynamicSearch {
    public static void main(String[] args) {

        String firstName = JOptionPane.showInputDialog("Filtro para el nombre: ");
        String lastName = JOptionPane.showInputDialog("Filtro para el apellido: ");
        String paymentMethod = JOptionPane.showInputDialog("Filtro para el método de pago: ");


        EntityManager entityManager = JpaUtil.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isBlank())
            predicates.add(criteriaBuilder.equal(from.get("firstName"), firstName));

        if (lastName != null && !lastName.isBlank())
            predicates.add(criteriaBuilder.equal(from.get("lastName"), lastName));

        if (paymentMethod != null && !paymentMethod.isBlank())
            predicates.add(criteriaBuilder.equal(from.get("paymentMethod"), paymentMethod));

        query.select(from).where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Customer> customers = entityManager.createQuery(query).getResultList();

        customers.forEach(System.out::println);

        entityManager.close();
    }
}
