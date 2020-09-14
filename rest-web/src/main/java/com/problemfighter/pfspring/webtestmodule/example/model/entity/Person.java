package com.problemfighter.pfspring.webtestmodule.example.model.entity;

import com.problemfighter.pfspring.restapi.inter.model.RestDTO;
import com.problemfighter.pfspring.restapi.inter.model.RestEntity;

import javax.persistence.*;

@Entity
public class Person implements RestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String firstName;
    public String lastName;

    @Column(unique = true)
    public String email;

    public String mobile;

    public String occupation;

    @Column(nullable = false)
    public String gender;

    @Column(nullable = false)
    public Double age;

    @Column(columnDefinition = "TEXT")
    public String address;

    public Boolean isDeleted = false;
}
