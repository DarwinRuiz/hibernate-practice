package org.druiz.hibernateapp.examples.customers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.druiz.hibernateapp.entities.Customer;
import org.druiz.hibernateapp.utils.JpaUtil;

import java.util.List;

public class HibernateCriteria {
    public static void main(String[] args) {

        EntityManager entityManager = JpaUtil.getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        System.out.println("=================== obtener todos los registros =================");
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);
        query.select(from);

        List<Customer> customers = entityManager.createQuery(query).getResultList();
        customers.forEach(System.out::println);


        System.out.println("=================== consulta con condiciones =================");
        CriteriaQuery<Customer> queryWhere = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> fromWithWhere = queryWhere.from(Customer.class);

        queryWhere.select(fromWithWhere).where(criteriaBuilder.equal(fromWithWhere.get("firstName"), "Kelly"));
        List<Customer> filteredCustomers = entityManager.createQuery(queryWhere).getResultList();
        filteredCustomers.forEach(System.out::println);


        System.out.println("=================== consulta con condiciones, usando like =================");
        CriteriaQuery<Customer> queryByLike = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> fromByLike = queryByLike.from(Customer.class);
        queryByLike.select(fromByLike).where(criteriaBuilder.like(fromByLike.get("firstName"), "%m%"));
        List<Customer> customersByLike = entityManager.createQuery(queryByLike).getResultList();
        customersByLike.forEach(System.out::println);


        System.out.println("=================== consulta con conjunción AND y disyunción OR =================");
        CriteriaQuery<Customer> queryWithLogicsOperatorsAnd = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> fromWithLogicsOperatorsAnd = queryWithLogicsOperatorsAnd.from(Customer.class);
        Predicate firstConditionAnd = criteriaBuilder.equal(fromWithLogicsOperatorsAnd.get("firstName"), "Carmen");
        Predicate secondConditionAnd = criteriaBuilder.equal(fromWithLogicsOperatorsAnd.get("paymentMethod"), "Transferencia");

        queryWithLogicsOperatorsAnd.select(fromWithLogicsOperatorsAnd).where(criteriaBuilder.and(firstConditionAnd, secondConditionAnd));
        List<Customer> customersFromLogicsOperatorsAnd = entityManager.createQuery(queryWithLogicsOperatorsAnd).getResultList();
        customersFromLogicsOperatorsAnd.forEach(System.out::println);


        CriteriaQuery<Customer> queryWithLogicsOperatorsOr = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> fromWithLogicsOperatorsOr = queryWithLogicsOperatorsOr.from(Customer.class);
        Predicate firstConditionOr = criteriaBuilder.equal(fromWithLogicsOperatorsOr.get("paymentMethod"), "Efectivo");
        Predicate secondConditionOr = criteriaBuilder.equal(fromWithLogicsOperatorsOr.get("paymentMethod"), "Transferencia");

        queryWithLogicsOperatorsOr.select(fromWithLogicsOperatorsOr).where(criteriaBuilder.or(firstConditionOr, secondConditionOr));
        List<Customer> customersFromLogicsOperatorsOr = entityManager.createQuery(queryWithLogicsOperatorsOr).getResultList();
        customersFromLogicsOperatorsOr.forEach(System.out::println);


        System.out.println("=================== consulta solo los nombres de los clientes =================");
        CriteriaQuery<String> queryNames = criteriaBuilder.createQuery(String.class);
        Root<Customer> fromByNamesOnly = queryNames.from(Customer.class);
        queryNames.select(fromByNamesOnly.get("firstName"));
        List<String> names = entityManager.createQuery(queryNames).getResultList();
        names.forEach(System.out::println);


        System.out.println("=================== consulta los métodos de pago unicos =================");
        CriteriaQuery<String> queryPaymentMethods = criteriaBuilder.createQuery(String.class);
        Root<Customer> fromByPaymentMethods = queryPaymentMethods.from(Customer.class);
        queryPaymentMethods.select(criteriaBuilder.upper(fromByPaymentMethods.get("paymentMethod"))).distinct(true);
        List<String> paymentMethods = entityManager.createQuery(queryPaymentMethods).getResultList();
        paymentMethods.forEach(System.out::println);


        System.out.println("=================== consulta solo los nombres completos de los clientes =================");
        CriteriaQuery<String> queryFullNames = criteriaBuilder.createQuery(String.class);
        Root<Customer> fromByFullNamesOnly = queryFullNames.from(Customer.class);
        Expression<String> firstNameExpression = fromByFullNamesOnly.get("firstName");
        Expression<String> lastNameExpression = fromByFullNamesOnly.get("lastName");

        queryFullNames.select(criteriaBuilder.concat(criteriaBuilder.concat(firstNameExpression, " "), lastNameExpression));
        List<String> fullNames = entityManager.createQuery(queryFullNames).getResultList();
        fullNames.forEach(System.out::println);


        System.out.println("=================== consulta campos personalizados de la entidad =================");
        CriteriaQuery<Object[]> queryCustomFields = criteriaBuilder.createQuery(Object[].class);
        Root<Customer> fromCustomFields = queryCustomFields.from(Customer.class);
        Expression<String> concatExpression = criteriaBuilder.concat(criteriaBuilder.concat(fromCustomFields.get("firstName"), " "), fromCustomFields.get("lastName"));
        queryCustomFields.multiselect(fromCustomFields.get("customerId"), concatExpression);
        List<Object[]> customCustomersData = entityManager.createQuery(queryCustomFields).getResultList();
        customCustomersData.forEach(customCustomerData -> {
            Integer customerId = (Integer) customCustomerData[0];
            String customerFullName = (String) customCustomerData[1];

            System.out.println("customerId = " + customerId + ", customerFullname = " + customerFullName);
        });


        System.out.println("=================== consulta cantidad de registros de la tabla/entidad =================");
        CriteriaQuery<Long> queryCount = criteriaBuilder.createQuery(Long.class);
        Root<Customer> fromCount = queryCount.from(Customer.class);
        queryCount.select(criteriaBuilder.count(fromCount));
        Long totalRecords = entityManager.createQuery(queryCount).getSingleResult();
        System.out.println("totalRecords = " + totalRecords);

        entityManager.close();
    }
}
