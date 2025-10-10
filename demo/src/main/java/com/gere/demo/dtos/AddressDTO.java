package com.gere.demo.dtos;

import java.util.List;

public record AddressDTO(
    Integer id,
    String addressType,
    String street,
    String city,
    String zipCode,
    String country,
    List<ContactDTO> contacts
) {}
