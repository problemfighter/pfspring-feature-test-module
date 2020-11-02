package com.problemfighter.pfspring.jdt.model.entity;

import com.problemfighter.pfspring.restapi.inter.model.RestEntity;
import javax.persistence.*;

@Entity
public class JdtPerson implements RestEntity {

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

    public Boolean isDeleted = false;

}
