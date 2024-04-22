package com.patecan.fullstackapp.services;

import com.patecan.fullstackapp.exception.DuplicateExceptionResponse;
import com.patecan.fullstackapp.exception.NotFoundResponse;
import com.patecan.fullstackapp.models.Customer;
import com.patecan.fullstackapp.models.CustomerRequest;
import com.patecan.fullstackapp.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerJPADao;

    public CustomerService(@Qualifier("CustomerJPABean") CustomerDao customerJPADao) {
        this.customerJPADao = customerJPADao;
    }

    public List<Customer> getAllCustomers(){
        return customerJPADao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id){
        return customerJPADao.selectCustomerById(id)
                .orElseThrow(()-> new NotFoundResponse("Customer with ID [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRequest customerRequest){
        // Check if email existed
        if(customerJPADao.checkExistedEmail(customerRequest.email())){
            throw new DuplicateExceptionResponse("User with this email already existed");
        }

        customerJPADao.insertCustomer(new Customer(customerRequest.name(), customerRequest.email(), customerRequest.age()));
    }
}
