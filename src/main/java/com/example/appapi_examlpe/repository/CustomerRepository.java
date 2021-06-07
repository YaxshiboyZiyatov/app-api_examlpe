package com.example.appapi_examlpe.repository;

import com.example.appapi_examlpe.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);


    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
