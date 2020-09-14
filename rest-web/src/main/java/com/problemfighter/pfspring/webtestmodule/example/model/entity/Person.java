package com.problemfighter.pfspring.webtestmodule.example.model.entity;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String firstName;
    public String lastName;
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
