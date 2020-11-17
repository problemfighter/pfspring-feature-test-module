package com.problemfighter.pfspring.rdbmser.model.dto;

import com.problemfighter.pfspring.rdbmser.model.data.Gender;
import com.problemfighter.pfspring.rdbmser.model.entity.Person;

import javax.persistence.*;


public class ProfileDTO {

    public Long id;
    public Gender gender;
    public String mobile;
    public String identity;
    public Double age;
    public PersonDTO person;
}
