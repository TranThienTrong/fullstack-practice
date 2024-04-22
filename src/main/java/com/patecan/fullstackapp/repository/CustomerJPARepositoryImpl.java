package com.patecan.fullstackapp.repository;

import com.patecan.fullstackapp.dao.CustomerDao;
import com.patecan.fullstackapp.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("CustomerJPABean")
public class CustomerJPARepositoryImpl implements CustomerDao {
    private final CustomerRepository customerRepository;

    public CustomerJPARepositoryImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean checkExistedEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }
}
