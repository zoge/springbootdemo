package com.gere.demo.dtos;

import com.gere.demo.dtos.enums.ContactType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactUpdateReq(
        @NotNull
        Integer personId,
        @NotNull
        ContactType type,
        @NotBlank
        @Size(max = 255)
        String value) {

}
