package com.gere.demo.service;

import com.gere.demo.dto.ContactCreateRequest;
import com.gere.demo.dto.ContactResponse;
import com.gere.demo.dto.ContactUpdateRequest;
import com.gere.demo.dto.ContactType;
import com.gere.demo.entity.Address;
import com.gere.demo.entity.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gere.demo.repository.ContactRepository;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repo;
    private final AddressService addressService;

    public ContactResponse create(ContactCreateRequest req) {
        Address address = addressService.require(req.addressId());
        Contact c = Contact.builder()
                .address(address)
                .contactType(req.type().name())
                .contactValue(req.value())
                .build();
        c = repo.save(c);
        return toRes(c);
    }

    public Page<ContactResponse> list(Integer addressId, Pageable pageable) {
        if (addressId != null) {
            return repo.findByAddressId(addressId, pageable).map(this::toRes);
        }
        return repo.findAll(pageable).map(this::toRes);
    }

    public ContactResponse get(Integer id) {
        return toRes(require(id));
    }

    public ContactResponse update(Integer id, ContactUpdateRequest req) {
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

    private ContactResponse toRes(Contact c) {
        return new ContactResponse(c.getId(), c.getAddress().getId(),c.getContactType(),
                c.getContactValue());
    }
}
