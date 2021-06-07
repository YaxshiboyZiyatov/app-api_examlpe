package com.example.appapi_examlpe.service;

import com.example.appapi_examlpe.entity.Customer;
import com.example.appapi_examlpe.payload.ApiResponse;
import com.example.appapi_examlpe.payload.CustomerDto;
import com.example.appapi_examlpe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    
    public List<Customer> getAll(){
        List<Customer> all = customerRepository.findAll();
        return all;
    }
    public Customer getById(Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
//        if (optionalCustomer.isPresent()){
//            return optionalCustomer.get();
//        }
//        return null;
//    }

        return optionalCustomer.orElse(null);
    }
    public ApiResponse add(CustomerDto customerDto){
        boolean exists = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (exists){
            return new ApiResponse("Bunday mijoz mavjud", false);
        }
        Customer customer=new Customer();
        customer.setAddress(customerDto.getAddress());
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        customerRepository.save(customer);

        return new ApiResponse("added", true);
    }
    public ApiResponse edit(Integer id, CustomerDto customerDto){
        boolean exists = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (exists){
            return new ApiResponse("Bunday telefon raqamli customer majvud", false);
        }
        Optional<Customer> byId = customerRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Not found Id", false);
        }
        Customer customer = byId.get();
        customer.setAddress(customerDto.getAddress());
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        customerRepository.save(customer);

        return new ApiResponse("added", true);

    }
    public ApiResponse delete(Integer id){
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent()){
            customerRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        }
        return new ApiResponse("not found id", false);
    }


}
