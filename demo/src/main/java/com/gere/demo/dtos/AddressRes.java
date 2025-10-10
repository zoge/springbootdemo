package com.gere.demo.dtos;

import com.gere.demo.dtos.enums.AddressType;

public record AddressRes(
  Integer id,
  Integer personId,
  AddressType type,
  String street,
  String city,
  String zipCode,
  String country){
}
