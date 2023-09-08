package com.secretroomwebsite.users;

import com.secretroomwebsite.AbstractTestcontainers;
import com.secretroomwebsite.customer.Customer;
import com.secretroomwebsite.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static com.secretroomwebsite.TestData.getTestCustomer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;
    @BeforeEach
    void setUp() {


    }


    @Test
    void itShouldSaveCustomer() {
        //Given
        Customer customer = getTestCustomer();
        //When
        Customer savedCustomer = underTest.save(customer);
        //Then
        assertThat(savedCustomer.getId()).isNotNull();

    }



    @Test
    void itShouldFindByPhone() {
        // Given
        Customer customer = getTestCustomer();

        // When
        Customer savedCustomer = underTest.save(customer);

        // When
        Optional<Customer> optionalFoundCustomer = underTest.findByPhone(customer.getPhone());

        // Then
        assertTrue(optionalFoundCustomer.isPresent(), "Customer was not found");

        Customer foundCustomer = optionalFoundCustomer.get();
        assertThat(foundCustomer).isEqualTo(savedCustomer);
        assertThat(foundCustomer.getPhone()).isEqualTo(customer.getPhone());
        assertThat(foundCustomer.getId()).isNotNull();
    }

    @Test
    void itShouldFindByEmail() {
        // Given
        Customer customer = getTestCustomer();

        // When
        Customer savedCustomer = underTest.save(customer);

        // When
        Optional<Customer> optionalFoundCustomer = underTest.findByEmail(customer.getEmail());

        // Then
        assertTrue(optionalFoundCustomer.isPresent(), "Customer was not found");

        Customer foundCustomer = optionalFoundCustomer.get();
        assertThat(foundCustomer).isEqualTo(savedCustomer);
        assertThat(foundCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(foundCustomer.getId()).isNotNull();
    }
}