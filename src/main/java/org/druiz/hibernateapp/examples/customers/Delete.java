package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import javax.swing.*;
import java.util.Arrays;

public class Delete {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

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
                    ", desea continuar con la eliminación?");


            if (confirmUserResponse != 0) {
                System.exit(0);
            }

            em.remove(customer);

            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El cliente ha sido eliminado con éxito!");
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
