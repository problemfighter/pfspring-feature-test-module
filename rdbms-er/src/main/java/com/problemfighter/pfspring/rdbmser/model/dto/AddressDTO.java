package com.problemfighter.pfspring.rdbmser.model.dto;

import com.problemfighter.pfspring.rdbmser.model.entity.Person;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

public class AddressDTO {

    public Long id;
    public String country;
    public String city;
    public String region;
    public String postalCode;
    public String details;
    public PersonDTO person;
}
