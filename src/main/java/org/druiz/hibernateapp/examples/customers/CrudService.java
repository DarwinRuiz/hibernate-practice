package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.services.CustomerService;
import org.druiz.hibernateapp.services.CustomerServiceImpl;
import org.druiz.hibernateapp.utils.JpaUtil;

import java.util.List;
import java.util.Optional;

public class CrudService {
    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        CustomerService service = new CustomerServiceImpl(entityManager);

        System.out.println("========== LISTAR ==========");

        List<Customer> customers = service.getAll();
        customers.forEach(System.out::println);

        System.out.println("========== BUSCAR ==========");

        Optional<Customer> customerFound = service.getById(20);
        System.out.println("customerFound = " + customerFound.orElse(null));


        System.out.println("========== CREAR ==========");
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Jhon");
        newCustomer.setLastName("Doe");
        newCustomer.setPaymentMethod("PayPal");
        service.save(newCustomer);


        System.out.println("========== ACTUALIZAR ==========");
        if (customerFound.isPresent()) {
            Customer customerForUpdate = customerFound.get();
            customerForUpdate.setPaymentMethod("Efectivo");
            service.save(customerForUpdate);
        }


        System.out.println("========== ELIMINAR ==========");
        if (customerFound.isPresent()) {
            Customer customerForDelete = customerFound.get();
            service.delete(customerForDelete);
        }


        entityManager.close();
    }
}
