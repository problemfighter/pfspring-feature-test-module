package com.problemfighter.pfspring.webtestmodule.example.model.dto.person;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class PersonMasterDTO {

    @JsonIgnore
    public Integer id;

    @NotNull(message = "Please enter first name")
    public String firstName;


    public String lastName;
    public String mobile;

    @NotNull(message = "Please enter gender")
    public String gender;

    @NotNull(message = "Please enter age")
    public Double age;

    @JsonProperty
    public Integer getId() {
        return id;
    }
}