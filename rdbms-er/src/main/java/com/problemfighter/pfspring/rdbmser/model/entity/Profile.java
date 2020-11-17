package com.problemfighter.pfspring.rdbmser.model.entity;

import com.problemfighter.pfspring.rdbmser.model.data.Gender;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public Gender gender;
    public String mobile;
    public String identity;

    @Column(nullable = false)
    public Double age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    public Person person;
}
