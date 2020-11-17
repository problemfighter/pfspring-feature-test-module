package com.problemfighter.pfspring.rdbmser.model.entity;

import com.problemfighter.pfspring.rdbmser.model.data.DegreeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public DegreeType type;
    public String description;
}
