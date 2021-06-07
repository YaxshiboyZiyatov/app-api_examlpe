package com.example.appapi_examlpe.controller;

import com.example.appapi_examlpe.entity.Customer;
import com.example.appapi_examlpe.payload.ApiResponse;
import com.example.appapi_examlpe.payload.CustomerDto;
import com.example.appapi_examlpe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerControllerSecond {
    @Autowired
    CustomerService customerService;


    @GetMapping("/api/customers")
    public ResponseEntity<List<Customer>> get(){
        List<Customer> all = customerService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/api/customers/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Customer byId = customerService.getById(id);
        return ResponseEntity.ok(byId);
    }

    @PostMapping("/api/customers")
    public HttpEntity<ApiResponse> added(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse add = customerService.add(customerDto);
//       if (add.isSuccess()){
//           ResponseEntity.status(201).body(add)
//       }
//       return ResponseEntity.status(HttpStatus.CONFLICT).body(add);
        return ResponseEntity.status(add.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(add);
    }

    @PutMapping("/api/customers/{id}")
    public HttpEntity<?> edited(@PathVariable Integer id,@Valid @RequestBody CustomerDto customerDto){
        ApiResponse edit = customerService.edit(id, customerDto);
//        if (edit.isSuccess()){
//            return ResponseEntity.status(201).body(edit);
//        }
//        return ResponseEntity.status(409).body(edit);
        return ResponseEntity.status(edit.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(edit);



    }

    @DeleteMapping("/api/customers/{id}")
    public HttpEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse delete = customerService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?202:409).body(delete);

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
