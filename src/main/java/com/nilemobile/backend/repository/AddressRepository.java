package com.nilemobile.backend.repository;

import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.reponse.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserUserId(Long userId);
}
