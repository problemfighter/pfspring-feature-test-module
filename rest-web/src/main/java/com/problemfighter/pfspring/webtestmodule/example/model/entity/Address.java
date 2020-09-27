package com.problemfighter.pfspring.webtestmodule.example.model.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String details;
    public String postCode;
    public String country;
    public String stateDistrict;
    public String cityUpozila;
}
