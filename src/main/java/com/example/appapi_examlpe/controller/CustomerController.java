package com.example.appapi_examlpe.controller;

import com.example.appapi_examlpe.entity.Customer;
import com.example.appapi_examlpe.payload.ApiResponse;
import com.example.appapi_examlpe.payload.CustomerDto;
import com.example.appapi_examlpe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/api/customer")
    public List<Customer> get(){
        return customerService.getAll();
    }

    @GetMapping("/api/customer/{id}")
    public Customer getById(@PathVariable Integer id){
        return customerService.getById(id);
    }

    @PostMapping("/api/customer")
    public ApiResponse added(@Valid @RequestBody CustomerDto customerDto){
        return customerService.add(customerDto);
    }

    @PutMapping("/api/customer/{id}")
    public ApiResponse edited(@PathVariable Integer id,@Valid @RequestBody CustomerDto customerDto){
        return customerService.edit(id,customerDto);
    }

    @DeleteMapping("/api/customer/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return customerService.delete(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
