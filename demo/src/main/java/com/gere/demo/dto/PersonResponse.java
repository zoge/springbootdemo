package com.gere.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public record PersonResponse(
        Integer id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,
        Date createdAt
        ) {

}
