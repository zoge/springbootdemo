package com.gere.demo.services;

import com.gere.demo.controller.exception.AddressTypeAlreadyExistsException;
import com.gere.demo.service.AddressService;
import com.gere.demo.controller.exception.MaxAddressesExceededException;
import com.gere.demo.dto.AddressCreateRequest;
import com.gere.demo.dto.AddressType;
import com.gere.demo.entity.Address;
import com.gere.demo.entity.Person;
import com.gere.demo.repository.AddressRepository;
import com.gere.demo.repository.PersonRepository;
import com.gere.demo.service.PersonService;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    AddressRepository repo;
    @Mock
    PersonService personService;
    @Mock
    PersonRepository personRepo;
    @InjectMocks
    AddressService addressService;
    
    @Test
    void addAddress_whenAlreadyHasTwo_shouldThrow() {
        Person p = new Person();
        p.setId(1);

        AddressCreateRequest req = new AddressCreateRequest(1, AddressType.TEMPORARY, "", "", "", "");
        
       // Mockito.when(personRepo.findByIdWithDetails(1)).thenReturn(Optional.of(p));
        Mockito.when(repo.countByPersonId(p.getId())).thenReturn(3);
       // Mockito.when(repo.save(addressP)).thenReturn(addressP);
        Mockito.when(personService.require(1)).thenReturn(p);

        MaxAddressesExceededException assertThrows = Assertions.assertThrows(MaxAddressesExceededException.class,
                () -> addressService.create(req));

        verify(personRepo, never()).save(any());
    }
    
        @Test
    void addAddress_whenAlreadyHasTwo_shouldThrow2() {
        Person p = new Person();
        p.setId(1);
//       
        AddressCreateRequest req = new AddressCreateRequest(1, AddressType.TEMPORARY, "", "", "", "");
        
       // Mockito.when(personRepo.findByIdWithDetails(1)).thenReturn(Optional.of(p));
        Mockito.when(repo.countByPersonId(p.getId())).thenReturn(2);
        Mockito.when(repo.existsByPersonIdAndAddressType(1, "TEMPORARY")).thenReturn(true);
       // Mockito.when(repo.save(addressP)).thenReturn(addressP);
        Mockito.when(personService.require(1)).thenReturn(p);

        AddressTypeAlreadyExistsException assertThrows = Assertions.assertThrows(AddressTypeAlreadyExistsException.class,
                () -> addressService.create(req));

        verify(personRepo, never()).save(any());
    }
}
