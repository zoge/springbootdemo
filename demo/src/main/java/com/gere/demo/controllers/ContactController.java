package com.gere.demo.controllers;

import com.gere.demo.dtos.ContactCreateReq;
import com.gere.demo.dtos.ContactRes;
import com.gere.demo.dtos.ContactUpdateReq;
import com.gere.demo.services.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@Tag(name = "Contacts", description = "Elérhetőség kezelés")
@RequiredArgsConstructor
public class ContactController {
  private final ContactService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ContactRes create(@RequestBody @Valid ContactCreateReq req) {
    return service.create(req);
  }

  @GetMapping
  public Page<ContactRes> list(@RequestParam(required = false) Integer personId,
                               @RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="20") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return service.list(personId, pageable);
  }

  @GetMapping("/{id}")
  public ContactRes get(@PathVariable Integer id) { return service.get(id); }

  @PatchMapping("/{id}")
  public ContactRes update(@PathVariable Integer id,
                           @RequestBody @Valid ContactUpdateReq req) {
    return service.update(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) { service.delete(id); }
}