package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import org.druiz.hibernateapp.domain.CustomerDto;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import java.util.List;

public class HibernateQL {
    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        System.out.println("=============== obtener todos ====================");

        List<Customer> customers = entityManager.createQuery("SELECT C FROM Customer C", Customer.class).getResultList();
        customers.forEach(System.out::println);


        System.out.println("============== consultar por id =================");
        Customer customer = (Customer) entityManager.createQuery("SELECT C FROM Customer C WHERE C.customerId = :customerId")
                .setParameter("customerId", 20)
                .getSingleResult();
        System.out.println("customer = " + customer);


        System.out.println("================== obtener solo el nombre del cliente por el id =================");
        String customerName = (String) entityManager.createQuery("SELECT C.firstName FROM Customer C WHERE C.customerId = :customerId", String.class)
                .setParameter("customerId", 20)
                .getSingleResult();
        System.out.println("customerName = " + customerName);


        System.out.println("=================== consulta por campos personalizados =================");
        Object[] customersObject = entityManager.createQuery("SELECT C.firstName, C.lastName FROM Customer C WHERE C.customerId = :customerId", Object[].class)
                .setParameter("customerId", 20)
                .getSingleResult();
        String customerFullName = customersObject[0].toString() + " " + customersObject[1].toString();
        System.out.println("customersObjects = " + customerFullName);


        System.out.println("=================== consulta por campos personalizados Lista =================");
        List<Object[]> customersObjects = entityManager.createQuery("SELECT C.firstName, C.lastName FROM Customer C", Object[].class)
                .getResultList();

        customersObjects.forEach(customersFullNames -> {
            System.out.println(customersFullNames[0].toString() + " " + customersFullNames[1].toString());
        });


        System.out.println("=================== consulta por cliente y forma de pagos =================");
        List<Object[]> customerWithPaymentMethods = entityManager.createQuery("SELECT C, C.paymentMethod FROM Customer C", Object[].class).getResultList();
        customerWithPaymentMethods.forEach(customerWithPaymentMethod -> {
            Customer customCustomer = (Customer) customerWithPaymentMethod[0];
            String paymentMethod = (String) customerWithPaymentMethod[1];
            System.out.println(customCustomer + ", " + paymentMethod);
        });


        System.out.println("=================== consulta transformada en una clase entity personalizada =================");
        List<Customer> customersInObjects = entityManager.createQuery("SELECT new Customer(C.firstName, C.lastName) FROM Customer C", Customer.class).getResultList();
        customersInObjects.forEach(System.out::println);


        System.out.println("=================== consulta transformada en una clase DTO personalizada =================");
        List<CustomerDto> customersWithDto = entityManager.createQuery("SELECT new org.druiz.hibernateapp.domain.CustomerDto(C.firstName, C.lastName) FROM Customer C", CustomerDto.class).getResultList();
        customersWithDto.forEach(System.out::println);


        System.out.println("=================== consulta lista de formas de pago unicas =================");
        List<String> paymentMethods = entityManager.createQuery("SELECT DISTINCT C.paymentMethod FROM Customer C", String.class).getResultList();
        paymentMethods.forEach(System.out::println);


        System.out.println("=================== consulta cantidad de registros =================");
        Long totalRecords = entityManager.createQuery("SELECT COUNT(1) FROM Customer", Long.class).getSingleResult();
        System.out.println("totalRecords = " + totalRecords);


        System.out.println("=================== consulta nombre y apellido concatenados =================");
        List<String> fullNames = entityManager.createQuery("SELECT CONCAT(C.firstName, ' ', C.lastName) FROM Customer C", String.class).getResultList();
        fullNames.forEach(System.out::println);


        System.out.println("=================== consulta para buscar por nombre =================");
        List<Customer> customersByLike = entityManager.createQuery("SELECT C FROM Customer C WHERE C.firstName LIKE :firstName", Customer.class)
                .setParameter("firstName", "%Kel%")
                .getResultList();
        customersByLike.forEach(System.out::println);


        System.out.println("=================== consulta del nombre más corto con su largo (subconsultas) =================");
        Object[] firstNameWithLength = entityManager.createQuery("SELECT C.firstName, length(C.firstName) FROM Customer C WHERE length(C.firstName) = (SELECT min(length(C.firstName)) FROM Customer C)", Object[].class)
                .getSingleResult();
        String firstName = (String) firstNameWithLength[0];
        Integer firstNameLength = (Integer) firstNameWithLength[1];
        System.out.println(firstName + ", Largo = " + firstNameLength);
        entityManager.close();
    }
}
