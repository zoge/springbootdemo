package com.gere.demo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public record PersonRes(
        Integer id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,
        Date createdAt
        ) {

}
