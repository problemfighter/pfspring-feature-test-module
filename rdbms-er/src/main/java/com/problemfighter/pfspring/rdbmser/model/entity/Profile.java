package com.problemfighter.pfspring.rdbmser.model.entity;

import com.problemfighter.pfspring.rdbmser.model.data.Gender;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Gender gender;
    public String mobile;
    public String identity;
    public Double age;
}
