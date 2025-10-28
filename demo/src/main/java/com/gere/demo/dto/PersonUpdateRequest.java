package com.gere.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import java.util.Date;

public record PersonUpdateRequest(
  @Size(max=80) String firstName,
  @Size(max=80) String lastName,
  @JsonFormat(pattern = "yyyy-MM-dd") Date birthDate
) {}