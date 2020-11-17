package com.problemfighter.pfspring.rdbmser.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String firstName;

    public String lastName;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String password;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Address> addresses = new HashSet<>();

    @OneToOne(mappedBy = "person")
    public Profile profile;

    @ManyToMany(mappedBy = "person")
    public Set<Degree> degree = new HashSet<>();
}
