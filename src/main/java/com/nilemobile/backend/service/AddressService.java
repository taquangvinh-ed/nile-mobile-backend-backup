package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.AddressException;
import com.nilemobile.backend.model.Address;

import java.util.List;

public interface AddressService {

    public List<Address> getAddressesByUserId(Long userId) throws AddressException;

    public Address addAddress(Address address, Long userId) throws AddressException;

    public Address updateAddress(Address address, Long userId) throws AddressException;

    public void deleteAddress(Long userId, Long addressId) throws AddressException;



}
