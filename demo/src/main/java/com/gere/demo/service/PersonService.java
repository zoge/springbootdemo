package com.gere.demo.service;

import com.gere.demo.controller.exception.PersonNotFoundException;
import com.gere.demo.dto.AddressContactResponse;
import com.gere.demo.dto.ContactResponse;
import com.gere.demo.dto.PersonCreateRequest;
import com.gere.demo.dto.PersonAdressListResponse;
import com.gere.demo.dto.PersonListResponse;
import com.gere.demo.dto.PersonResponse;
import com.gere.demo.dto.PersonUpdateRequest;
import com.gere.demo.entity.Person;
import com.gere.demo.repository.PersonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repo;

    public List<PersonListResponse> getAllPersons() {
        return repo.findAll().stream()
                .map(p -> new PersonListResponse(p.getId(), p.getFirstName(), p.getLastName()))
                .toList();
    }

    public Page<PersonListResponse> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable) {
        return repo.findByLastNameContainingIgnoreCase(lastName, pageable)
                .map(PersonListResponse::from);
    }

    // Egy személy részletesen, címekkel és kontaktokkal
    public PersonAdressListResponse getPerson(Integer id) {
        Person p = repo.findByIdWithDetails(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return new PersonAdressListResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getAddresses().stream()
                        .map(a -> new AddressContactResponse(
                        a.getId(),
                        a.getAddressType(),
                        a.getStreet(),
                        a.getCity(),
                        a.getZipCode(),
                        a.getCountry(),
                        a.getContacts().stream()
                                .map(c -> new ContactResponse(
                                c.getId(),
                                null,
                                c.getContactType(),
                                c.getContactValue()))
                                .toList()
                )).toList()
        );
    }

    public PersonResponse create(PersonCreateRequest req) {
        Person p = Person.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .birthDate(req.birthDate())
                .build();
        p = repo.save(p);
        return toRes(p);
    }

    public PersonResponse update(Integer id, PersonUpdateRequest req) {
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

    private PersonResponse toRes(Person p) {
        return new PersonResponse(p.getId(), p.getFirstName(), p.getLastName(),
                p.getBirthDate(), p.getCreatedAt());
    }
}
