package com.gere.demo.controller;

import com.gere.demo.dto.AddressCreateRequest;
import com.gere.demo.dto.AddressResponse;
import com.gere.demo.dto.AddressUpdateRequest;
import com.gere.demo.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Addresses", description = "Címek kezelése")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse create(@RequestBody @Valid AddressCreateRequest req) {
        return service.create(req);
    }

    @GetMapping
    public Page<AddressResponse> list(@RequestParam(required = false) Integer personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(personId, pageable);
    }

    @GetMapping("/{id}")
    public AddressResponse get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PatchMapping("/{id}")
    public AddressResponse update(@PathVariable Integer id,
            @RequestBody @Valid AddressUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
