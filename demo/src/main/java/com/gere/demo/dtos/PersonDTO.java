package com.gere.demo.dtos;

import java.util.List;

public record PersonDTO(
        Integer id,
        String firstName,
        String lastName,
        List<AddressDTO> addresses
        ) {

}
