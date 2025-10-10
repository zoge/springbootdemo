package com.gere.demo.repositories;

import com.gere.demo.entites.Person;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    // név szerinti keresés (case-insensitive)
    Page<Person> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Person p LEFT JOIN FETCH p.addresses a LEFT JOIN FETCH a.contacts WHERE p.id = :id")
    Optional<Person> findByIdWithDetails(Integer id);
}
