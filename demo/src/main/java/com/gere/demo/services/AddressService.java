package com.gere.demo.services;

import com.gere.demo.dtos.AddressCreateReq;
import com.gere.demo.dtos.AddressRes;
import com.gere.demo.dtos.AddressUpdateReq;
import com.gere.demo.dtos.enums.AddressType;
import com.gere.demo.entites.Address;
import com.gere.demo.entites.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gere.demo.repositories.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repo;
    private final PersonService personService;

    public AddressRes create(AddressCreateReq req) {
        Person person = personService.require(req.personId());
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

    public Page<AddressRes> list(Integer personId, Pageable pageable) {
        if (personId != null) {
            return repo.findByPersonId(personId, pageable).map(this::toRes);
        }
        return repo.findAll(pageable).map(this::toRes);
    }

    public AddressRes get(Integer id) {
        return toRes(require(id));
    }

    public AddressRes update(Integer id, AddressUpdateReq req) {
        Address a = require(id);
        if (req.type() != null) {
            a.setAddressType(req.type().name());
        }
        if (req.city() != null) {
            a.setCity(req.city());
        }
        if (req.zipCode()!= null) {
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

    private AddressRes toRes(Address a) {
        return new AddressRes(a.getId(), a.getPerson().getId(), AddressType.valueOf(a.getAddressType()),
                a.getStreet(), a.getCity(), a.getZipCode(), a.getCountry());
    }

}
