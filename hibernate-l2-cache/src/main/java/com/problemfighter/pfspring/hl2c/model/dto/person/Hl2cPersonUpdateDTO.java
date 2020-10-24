package com.problemfighter.pfspring.hl2c.model.dto.person;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hl2cPersonUpdateDTO extends Hl2cPersonDetailDTO {
    public Long id;

    public Long getId() {
        return id;
    }
}
