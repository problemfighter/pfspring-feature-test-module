package com.problemfighter.pfspring.rdbmser.model.entity;

import com.problemfighter.pfspring.rdbmser.model.data.DegreeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public DegreeType type;

    @Column(columnDefinition="TEXT")
    public String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    public Set<Person> person = new HashSet<>();
}
