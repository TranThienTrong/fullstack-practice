package com.patecan.fullstackapp.repository;


import com.patecan.fullstackapp.beans.CustomerRowMapper;
import com.patecan.fullstackapp.dao.CustomerDao;
import com.patecan.fullstackapp.models.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("CustomerJDBCBean")
public class CustomerJDBCRepositoryImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCRepositoryImpl(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT * 
                FROM customer
                """;

        List<Customer> customerList = jdbcTemplate.query(sql, customerRowMapper);
        return customerList;
    }


    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        var sql = """
                SELECT * 
                FROM customer 
                WHERE id = ?
                """;

        return jdbcTemplate
                .query(sql, customerRowMapper, customerId)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?, ?, ?)
                """;

        int update = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println(update);
    }

    @Override
    public boolean checkExistedEmail(String email) {
        return false;
    }
}
