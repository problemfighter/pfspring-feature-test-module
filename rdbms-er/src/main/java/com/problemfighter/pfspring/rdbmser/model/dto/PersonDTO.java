package com.problemfighter.pfspring.rdbmser.model.dto;


import java.util.HashSet;
import java.util.Set;


public class PersonDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public Set<AddressDTO> addresses = new HashSet<>();
    public ProfileDTO profile;
    public Set<DegreeDTO> degree = new HashSet<>();
}
