package com.gere.demo.controller;

import com.gere.demo.dto.PersonCreateRequest;
import com.gere.demo.dto.PersonAdressListResponse;
import com.gere.demo.dto.PersonListResponse;
import com.gere.demo.dto.PersonResponse;
import com.gere.demo.dto.PersonUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gere.demo.service.PersonService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/persons")
@Tag(name = "Persons", description = "Személyek kezelése")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping("/search")
    @Operation(summary = "Személyek listázása", description = "Lapozható lista, név szerinti kereséssel.")
    public Page<PersonListResponse> list(
            @Parameter(description = "Keresés vezetéknévre")
            @RequestParam(required = false, defaultValue = "") String lastName,
            @ParameterObject Pageable pageable
    ) {
        return service.findByLastNameContainingIgnoreCase(lastName, pageable);
    }

    @GetMapping
    @Operation(summary = "Személyek listázása", description = "Összes személy listázása")
    public ResponseEntity<List<PersonListResponse>> getAllPersons() {
        return ResponseEntity.ok(service.getAllPersons());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse create(@RequestBody @Valid PersonCreateRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonAdressListResponse> getPerson(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getPerson(id));
    }

    @PatchMapping("/{id}")
    public PersonResponse update(@PathVariable Integer id,
            @RequestBody @Valid PersonUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
