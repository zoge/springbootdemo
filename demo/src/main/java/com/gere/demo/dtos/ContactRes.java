package com.gere.demo.dtos;

import com.gere.demo.dtos.enums.ContactType;

public record ContactRes(
        Integer id,
        Integer addressId,
        ContactType type,
        String value
        ) {

}
