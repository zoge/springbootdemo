package com.gere.demo.repository;

import com.gere.demo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Page<Contact> findByAddressId(Integer addressId, Pageable pageable);
}
