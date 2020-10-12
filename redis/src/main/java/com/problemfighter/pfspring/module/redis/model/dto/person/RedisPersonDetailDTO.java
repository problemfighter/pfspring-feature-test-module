package com.problemfighter.pfspring.module.redis.model.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.problemfighter.java.oc.annotation.DataMappingInfo;
import com.problemfighter.pfspring.module.redis.model.intercept.RedisPersonCopyIntercept;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DataMappingInfo(customProcessor = RedisPersonCopyIntercept.class)
public class RedisPersonDetailDTO extends RedisPersonMasterDTO {

    public String email;
    public String occupation;

}
