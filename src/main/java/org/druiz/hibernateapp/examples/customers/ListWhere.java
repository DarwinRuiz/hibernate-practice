package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import javax.swing.*;
import java.util.List;

public class ListWhere {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        List<String> paymentMethodsDB = em.createQuery("SELECT DISTINCT c.paymentMethod FROM Customer c", String.class).getResultList();
        String[] paymentMethods = paymentMethodsDB.toArray(String[]::new);

        String searchTerm = (String) JOptionPane.showInputDialog(
                null,
                "Selecciona un método de pago:",
                "Método de Pago",
                JOptionPane.QUESTION_MESSAGE,
                null,
                paymentMethods,
                paymentMethods[0]
        );


        if (searchTerm == null) {
            em.close();
            System.exit(0);
        }


        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.paymentMethod = ?1", Customer.class);
        query.setParameter(1, searchTerm);
        java.util.List<Customer> filteredCustomers = query.getResultList();

        filteredCustomers.forEach(System.out::println);

        em.close();
    }
}
