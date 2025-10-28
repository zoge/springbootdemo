package com.gere.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.annotation.Nullable;

@JsonInclude(Include.NON_NULL)
public record ContactResponse(
        Integer id,
        @Nullable
        Integer addressId,
        String type,
        String value
        ) {

}
