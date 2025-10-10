package com.gere.demo.controllers;

import com.gere.demo.dtos.AddressCreateReq;
import com.gere.demo.dtos.AddressRes;
import com.gere.demo.dtos.AddressUpdateReq;
import com.gere.demo.services.AddressService;
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
    public AddressRes create(@RequestBody @Valid AddressCreateReq req) {
        return service.create(req);
    }

    @GetMapping
    public Page<AddressRes> list(@RequestParam(required = false) Integer personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(personId, pageable);
    }

    @GetMapping("/{id}")
    public AddressRes get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PatchMapping("/{id}")
    public AddressRes update(@PathVariable Integer id,
            @RequestBody @Valid AddressUpdateReq req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
