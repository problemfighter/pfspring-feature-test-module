package com.problemfighter.pfspring.hl2c.model.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.problemfighter.java.oc.annotation.DataMappingInfo;
import com.problemfighter.pfspring.hl2c.model.intercept.Hl2cPersonCopyIntercept;


@JsonInclude(JsonInclude.Include.NON_NULL)
@DataMappingInfo(customProcessor = Hl2cPersonCopyIntercept.class)
public class Hl2cPersonDetailDTO extends Hl2cPersonMasterDTO {

    public String email;
    public String occupation;

}
