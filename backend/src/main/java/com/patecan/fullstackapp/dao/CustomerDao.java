package com.patecan.fullstackapp.dao;

import com.patecan.fullstackapp.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer customerId);
    void insertCustomer(Customer customer);
    boolean checkExistedEmail(String email);
}

