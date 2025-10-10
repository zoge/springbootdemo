package com.gere.demo.repositories;

import com.gere.demo.dtos.enums.AddressType;
import com.gere.demo.entites.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Page<Address> findByPersonId(Integer personId, Pageable pageable);
    
    int countByPersonId(Integer personId);

    boolean existsByPersonIdAndAddressType(Integer personId, String addressType);
    boolean existsByPersonIdAndAddressTypeAndIdNot(Integer personId, String addressType, Integer id); // update-hez
}
