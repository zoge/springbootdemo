package com.gere.demo.dto;

import com.gere.demo.entity.Person;

public record PersonListResponse(Integer id, String firstName, String lastName) {

    public static PersonListResponse from(Person p) {
        return new PersonListResponse(p.getId(), p.getFirstName(), p.getLastName());
    }
}
