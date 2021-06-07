package com.example.appapi_examlpe.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull(message = "fullName mavjud emas")
    private String fullName;

    @NotNull(message = "Phone number kiriting")
    private String phoneNumber;

    @NotNull(message = "address kiriting")
    private String address;

}
