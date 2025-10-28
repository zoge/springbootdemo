package com.gere.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactCreateRequest(@NotNull
        Integer addressId,
        @NotNull
        ContactType type,
        @NotBlank
        @Size(max = 120)
        String value) {

}
