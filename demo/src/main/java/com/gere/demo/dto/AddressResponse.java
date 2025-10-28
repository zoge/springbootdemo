package com.gere.demo.dto;

public record AddressResponse(
  Integer id,
  Integer personId,
  AddressType type,
  String street,
  String city,
  String zipCode,
  String country){
}
