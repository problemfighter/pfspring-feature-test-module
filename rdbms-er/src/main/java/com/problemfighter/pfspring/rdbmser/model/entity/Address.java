package com.problemfighter.pfspring.rdbmser.model.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Person person;
}
