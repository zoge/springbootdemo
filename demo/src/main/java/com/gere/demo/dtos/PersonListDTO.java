package com.gere.demo.dtos;

import com.gere.demo.entites.Person;

public record PersonListDTO(Integer id, String firstName, String lastName) {

    public static PersonListDTO from(Person p) {
        return new PersonListDTO(p.getId(), p.getFirstName(), p.getLastName());
    }
}
