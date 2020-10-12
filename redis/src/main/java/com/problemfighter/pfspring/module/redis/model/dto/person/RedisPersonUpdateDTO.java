package com.problemfighter.pfspring.module.redis.model.dto.person;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RedisPersonUpdateDTO extends RedisPersonDetailDTO {
    public Long id;

    public Long getId() {
        return id;
    }
}
