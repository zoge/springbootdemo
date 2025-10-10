package com.gere.demo.dtos;

import com.gere.demo.dtos.enums.AddressType;
import jakarta.validation.constraints.Size;

public record AddressUpdateReq(
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
