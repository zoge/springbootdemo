package com.gere.demo.services;

import com.gere.demo.controllers.exceptions.MaxAddressesExceededException;
import com.gere.demo.dtos.AddressCreateReq;
import com.gere.demo.dtos.enums.AddressType;
import com.gere.demo.entites.Address;
import com.gere.demo.entites.Person;
import com.gere.demo.repositories.PersonRepository;
import java.util.HashSet;
import java.util.Optional;
import org.hamcrest.Matchers;
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
    PersonRepository personRepo;
    @InjectMocks
    AddressService addressService;
    
    @Test
    void addAddress_whenAlreadyHasTwo_shouldThrow() {
        Person p = new Person();
        p.setAddresses(new HashSet<>());
        p.getAddresses().add(new Address());
        p.getAddresses().add(new Address());

        AddressCreateReq req = new AddressCreateReq(1, AddressType.TEMPORARY, "", "", "", "");
        
        Mockito.when(personRepo.findByIdWithDetails(1)).thenReturn(Optional.of(p));

        MaxAddressesExceededException assertThrows = Assertions.assertThrows(MaxAddressesExceededException.class,
                () -> addressService.create(req));

        verify(personRepo, never()).save(any());
    }
}
