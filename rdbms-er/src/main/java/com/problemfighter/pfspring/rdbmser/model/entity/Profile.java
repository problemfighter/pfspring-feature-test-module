package com.problemfighter.pfspring.rdbmser.model.entity;

import com.problemfighter.pfspring.rdbmser.model.data.Gender;

import javax.persistence.*;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Gender gender;
    public String mobile;
    public String identity;
    public Double age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    public Person person;
}
