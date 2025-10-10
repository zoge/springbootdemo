package com.gere.demo.controllers.exceptions;

import com.gere.demo.dtos.enums.AddressType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AddressTypeAlreadyExistsException extends RuntimeException {

    public AddressTypeAlreadyExistsException(Integer personId, AddressType type) {
        super("This type alread has defined: " + type + " (personId=" + personId + ")");
    }
}
