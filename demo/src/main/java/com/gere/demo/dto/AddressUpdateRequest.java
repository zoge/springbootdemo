package com.gere.demo.dto;

import jakarta.validation.constraints.Size;

public record AddressUpdateRequest(
        AddressType type,
        @Size(max = 200)
        String street,
        @Size(max = 100)
        String city,
        @Size(max = 20)
        String zipCode,
        @Size(max = 100)
        String country) {

}
