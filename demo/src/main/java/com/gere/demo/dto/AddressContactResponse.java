package com.gere.demo.dto;

import java.util.List;

public record AddressContactResponse(
    Integer id,
    String addressType,
    String street,
    String city,
    String zipCode,
    String country,
    List<ContactResponse> contacts
) {}
