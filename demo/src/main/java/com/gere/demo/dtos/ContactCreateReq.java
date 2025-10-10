package com.gere.demo.dtos;

import com.gere.demo.dtos.enums.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactCreateReq(@NotNull
        Integer addressId,
        @NotNull
        ContactType type,
        @NotBlank
        @Size(max = 120)
        String value) {

}
