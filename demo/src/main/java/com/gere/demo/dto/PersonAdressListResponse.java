package com.gere.demo.dto;

import java.util.List;

public record PersonAdressListResponse(
        Integer id,
        String firstName,
        String lastName,
        List<AddressContactResponse> addresses
        ) {

}
