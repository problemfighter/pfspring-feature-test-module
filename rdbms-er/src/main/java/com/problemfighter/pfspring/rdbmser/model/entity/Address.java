package com.problemfighter.pfspring.rdbmser.model.entity;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String country;
    public String city;
    public String region;
    public String postalCode;


    public String details;
}
