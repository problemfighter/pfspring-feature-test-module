package com.problemfighter.pfspring.webtestmodule.example.model.dto.person;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonUpdateDTO extends PersonDetailDTO {
    public Long id;

    public Long getId() {
        return id;
    }
}
