package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import javax.swing.*;

public class Create {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        String[] paymentMethods = {"Tarjeta de crédito", "Tarjeta de débito", "Efectivo", "PayPal", "Transferencia"};

        try {

            String firstName = JOptionPane.showInputDialog("Ingrese el nombre del cliente");
            String lastName = JOptionPane.showInputDialog("Ingrese el apellido del cliente");
            String paymentMethod = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona un método de pago:",
                    "Método de Pago",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    paymentMethods,
                    paymentMethods[0]
            );


            em.getTransaction().begin();

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPaymentMethod(paymentMethod);

            em.persist(customer);

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "El cliente ha sido creado con éxito!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(System.out);
        } finally {
            em.close();
        }
    }
}
