package com.patecan.fullstackapp.beans;

import com.patecan.fullstackapp.models.Customer;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @Test
    void mapRow() throws Exception {
        // GIVEN
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(18);
        when(resultSet.getString("name")).thenReturn("trong");
        when(resultSet.getString("email")).thenReturn("trong@gmail.com");

        // WHEN
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        // THEN
        Customer expected = new Customer(
                1, "trong", "trong@gmail.com", 18
        );

        assertEquals(expected, actual);

    }
}