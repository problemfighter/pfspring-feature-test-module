package com.problemfighter.pfspring.module.redis.model.dto.person;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.problemfighter.pfspring.restapi.inter.model.RestDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RedisPersonMasterDTO implements RestDTO, Serializable {

    @JsonIgnore
    public Long id;

    @NotNull(message = "Please enter first name")
    public String firstName;


    public String lastName;
    public String mobile;

    @NotNull(message = "Please enter gender")
    public String gender;

    @NotNull(message = "Please enter age")
    public Double age;

    @JsonProperty
    public Long getId() {
        return id;
    }
}
