package com.gere.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactUpdateRequest(
        @NotNull
        Integer personId,
        @NotNull
        ContactType type,
        @NotBlank
        @Size(max = 255)
        String value) {

}
