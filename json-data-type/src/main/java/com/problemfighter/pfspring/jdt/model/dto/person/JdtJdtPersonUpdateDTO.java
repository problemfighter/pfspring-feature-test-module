package com.problemfighter.pfspring.jdt.model.dto.person;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JdtJdtPersonUpdateDTO extends JdtJdtPersonDetailDTO {
    public Long id;

    public Long getId() {
        return id;
    }
}
