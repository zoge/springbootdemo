package com.gere.demo.controller;

import com.gere.demo.dto.ContactCreateRequest;
import com.gere.demo.dto.ContactResponse;
import com.gere.demo.dto.ContactUpdateRequest;
import com.gere.demo.service.ContactService;
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
  public ContactResponse create(@RequestBody @Valid ContactCreateRequest req) {
    return service.create(req);
  }

  @GetMapping
  public Page<ContactResponse> list(@RequestParam(required = false) Integer personId,
                               @RequestParam(defaultValue="0") int page,
                               @RequestParam(defaultValue="20") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return service.list(personId, pageable);
  }

  @GetMapping("/{id}")
  public ContactResponse get(@PathVariable Integer id) { return service.get(id); }

  @PatchMapping("/{id}")
  public ContactResponse update(@PathVariable Integer id,
                           @RequestBody @Valid ContactUpdateRequest req) {
    return service.update(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) { service.delete(id); }
}