package com.problemfighter.pfspring.webtestmodule.model.entity;

import com.problemfighter.pfspring.webtestmodule.model.common.EntityCommon;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Student extends EntityCommon {

    public String firstName;
    public String lastName;
    public String identity;
    public String mobile;


    @ManyToOne
    @JoinColumn(name = "department_id")
    public Department department;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    public Set<Registration> registrations = new HashSet<>();


}
