package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import javax.swing.*;
import java.util.Arrays;

public class Update {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        String[] paymentMethods = {"Tarjeta de crédito", "Tarjeta de débito", "Efectivo", "PayPal", "Transferencia"};


        try {

            String customerIdByUser = JOptionPane.showInputDialog("Ingrese el código del cliente");

            if (customerIdByUser == null || customerIdByUser.isBlank()) {
                JOptionPane.showMessageDialog(null, "El valor proporcionado no es un código númerico válido");
                System.exit(0);
            }

            em.getTransaction().begin();


            Integer customerId = Integer.parseInt(customerIdByUser);

            Query findQuery = em.createQuery("SELECT c FROM Customer c WHERE c.customerId = ?1", Customer.class);
            findQuery.setMaxResults(1);
            findQuery.setParameter(1, customerId);

            Customer customer = (Customer) findQuery.getSingleResult();

            int confirmUserResponse = JOptionPane.showConfirmDialog(null, "El cliente encontrado es: " +
                    customer.getFirstName() +
                    " " +
                    customer.getLastName() +
                    ", desea continuar con la edición?");


            if (confirmUserResponse != 0) {
                System.exit(0);
            }

            String firstName = JOptionPane.showInputDialog("Ingrese el nombre del cliente");
            String lastName = JOptionPane.showInputDialog("Ingrese el apellido del cliente");
            String paymentMethod = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona un método de pago:",
                    "Método de Pago",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    paymentMethods,
                    Arrays.stream(paymentMethods).filter(element -> element.equals(customer.getPaymentMethod())).findFirst().orElse(null)
            );

            if (firstName != null && !firstName.isBlank()) {
                customer.setFirstName(firstName);
            }

            if (lastName != null && !lastName.isBlank()) {
                customer.setLastName(lastName);
            }

            if (paymentMethod != null && !paymentMethod.isBlank()) {
                customer.setPaymentMethod(paymentMethod);
            }

            em.merge(customer);

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "El cliente ha sido actualizado con éxito!");
        } catch (NoResultException e) {
            JOptionPane.showMessageDialog(null, "El cliente con el código proporcionado no existe.");
            System.exit(0);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(System.out);
        } finally {
            em.close();
        }
    }
}
