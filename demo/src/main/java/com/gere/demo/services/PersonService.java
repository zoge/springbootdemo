package com.gere.demo.services;

import com.gere.demo.controllers.exceptions.PersonNotFoundException;
import com.gere.demo.dtos.AddressDTO;
import com.gere.demo.dtos.ContactDTO;
import com.gere.demo.dtos.PersonCreateReq;
import com.gere.demo.dtos.PersonDTO;
import com.gere.demo.dtos.PersonListDTO;
import com.gere.demo.dtos.PersonRes;
import com.gere.demo.dtos.PersonUpdateReq;
import com.gere.demo.entites.Person;
import com.gere.demo.repositories.PersonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repo;

    public List<PersonListDTO> getAllPersons() {
        return repo.findAll().stream()
                .map(p -> new PersonListDTO(p.getId(), p.getFirstName(), p.getLastName()))
                .toList();
    }

    public Page<PersonListDTO> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable) {
        return repo.findByLastNameContainingIgnoreCase(lastName, pageable)
                .map(PersonListDTO::from);
    }

    // Egy személy részletesen, címekkel és kontaktokkal
    public PersonDTO getPerson(Integer id) {
        Person p = repo.findByIdWithDetails(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return new PersonDTO(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getAddresses().stream()
                        .map(a -> new AddressDTO(
                        a.getId(),
                        a.getAddressType(),
                        a.getStreet(),
                        a.getCity(),
                        a.getZipCode(),
                        a.getCountry(),
                        a.getContacts().stream()
                                .map(c -> new ContactDTO(
                                c.getId(),
                                c.getContactType(),
                                c.getContactValue()))
                                .toList()
                )).toList()
        );
    }

    public PersonRes create(PersonCreateReq req) {
        Person p = Person.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .birthDate(req.birthDate())
                .build();
        p = repo.save(p);
        return toRes(p);
    }

    public PersonRes update(Integer id, PersonUpdateReq req) {
        Person p = require(id);
        if (req.firstName() != null) {
            p.setFirstName(req.firstName());
        }
        if (req.lastName() != null) {
            p.setLastName(req.lastName());
        }
        if (req.birthDate() != null) {
            p.setBirthDate(req.birthDate());
        }
        return toRes(repo.save(p));
    }

    public Person require(Integer id) {
        return repo.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Person not found: " + id));
    }

    public void delete(Integer id) {
        repo.delete(require(id));
    }

    private PersonRes toRes(Person p) {
        return new PersonRes(p.getId(), p.getFirstName(), p.getLastName(),
                p.getBirthDate(), p.getCreatedAt());
    }
}
