package com.gere.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressCreateRequest(
        @NotNull
        Integer personId,
        @NotNull
        AddressType type,
        @Size(max = 200)
        String street,        
        @NotBlank
        @Size(max = 100)
        String city,
        @NotBlank
        @Size(max = 20)
        String zip,
        @NotBlank
        @Size(max = 100)
        String country
        ) {

}
