package com.gere.demo.services;

import com.gere.demo.dtos.ContactCreateReq;
import com.gere.demo.dtos.ContactRes;
import com.gere.demo.dtos.ContactUpdateReq;
import com.gere.demo.dtos.enums.ContactType;
import com.gere.demo.entites.Address;
import com.gere.demo.entites.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gere.demo.repositories.ContactRepository;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repo;
    private final AddressService addressService;

    public ContactRes create(ContactCreateReq req) {
        Address address = addressService.require(req.addressId());
        Contact c = Contact.builder()
                .address(address)
                .contactType(req.type().name())
                .contactValue(req.value())
                .build();
        c = repo.save(c);
        return toRes(c);
    }

    public Page<ContactRes> list(Integer addressId, Pageable pageable) {
        if (addressId != null) {
            return repo.findByAddressId(addressId, pageable).map(this::toRes);
        }
        return repo.findAll(pageable).map(this::toRes);
    }

    public ContactRes get(Integer id) {
        return toRes(require(id));
    }

    public ContactRes update(Integer id, ContactUpdateReq req) {
        Contact c = require(id);
        if (req.type() != null) {
            c.setContactType(req.type().name());
        }
        if (req.value() != null) {
            c.setContactValue(req.value());
        }
        return toRes(repo.save(c));
    }

    public void delete(Integer id) {
        repo.delete(require(id));
    }

    public Contact require(Integer id) {
        return repo.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Contact not found: " + id));
    }

    private ContactRes toRes(Contact c) {
        return new ContactRes(c.getId(), c.getAddress().getId(), ContactType.valueOf(c.getContactType()),
                c.getContactValue());
    }
}
