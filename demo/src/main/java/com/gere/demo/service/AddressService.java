package com.gere.demo.service;

import com.gere.demo.controller.exception.AddressTypeAlreadyExistsException;
import com.gere.demo.controller.exception.MaxAddressesExceededException;
import com.gere.demo.dto.AddressCreateRequest;
import com.gere.demo.dto.AddressResponse;
import com.gere.demo.dto.AddressUpdateRequest;
import com.gere.demo.dto.AddressType;
import com.gere.demo.entity.Address;
import com.gere.demo.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gere.demo.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repo;
    private final PersonService personService;

    public AddressResponse create(AddressCreateRequest req) {
        Person person = personService.require(req.personId());
        if (repo.countByPersonId(req.personId())> 2) {
            throw new MaxAddressesExceededException(req.personId()); // -> 409
        }
        // max 1 per típus
        if (repo.existsByPersonIdAndAddressType(req.personId(), req.type().name())) {
            throw new AddressTypeAlreadyExistsException(req.personId(), req.type());
        }
        Address a = Address.builder()
                .person(person)
                .addressType(req.type().name())
                .city(req.city())
                .street(req.street())
                .zipCode(req.zip())
                .country(req.country())
                .build();
        a = repo.save(a);
        return toRes(a);
    }

    public Page<AddressResponse> list(Integer personId, Pageable pageable) {
        if (personId != null) {
            return repo.findByPersonId(personId, pageable).map(this::toRes);
        }
        return repo.findAll(pageable).map(this::toRes);
    }

    public AddressResponse get(Integer id) {
        return toRes(require(id));
    }

    public AddressResponse update(Integer id, AddressUpdateRequest req) {
        Address a = require(id);
        if (req.type() != null) {
            final String name = req.type().name();
            // Ha változik a cím típus és létezik másik olyan típus    
            if (!name.equals(a.getAddressType())
                    && repo.existsByPersonIdAndAddressTypeAndIdNot(a.getPerson().getId(),  req.type().name(), id)) {
                throw new AddressTypeAlreadyExistsException(a.getPerson().getId(), req.type());
            }
            a.setAddressType(name);
        }
        if (req.city() != null) {
            a.setCity(req.city());
        }
        if (req.zipCode() != null) {
            a.setZipCode(req.zipCode());
        }
        if (req.country() != null) {
            a.setCountry(req.country());
        }
        return toRes(repo.save(a));
    }

    public void delete(Integer id) {
        repo.delete(require(id));
    }

    public Address require(Integer id) {
        return repo.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Address not found: " + id));
    }

    private AddressResponse toRes(Address a) {
        return new AddressResponse(a.getId(), a.getPerson().getId(), AddressType.valueOf(a.getAddressType()),
                a.getStreet(), a.getCity(), a.getZipCode(), a.getCountry());
    }

}
