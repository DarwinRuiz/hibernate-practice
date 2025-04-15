package org.druiz.hibernateapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Column(name = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customers")
    @SequenceGenerator(name = "seq_customers", sequenceName = "seq_customers", allocationSize = 1)
    private Integer customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "payment_method")
    private String paymentMethod;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Customer{");
        sb.append("customerId=").append(customerId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", paymentMethod='").append(paymentMethod).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
