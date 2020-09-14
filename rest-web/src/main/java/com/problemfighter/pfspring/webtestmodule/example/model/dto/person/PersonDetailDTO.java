package com.problemfighter.pfspring.webtestmodule.example.model.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.problemfighter.java.oc.annotation.DataMappingInfo;
import com.problemfighter.pfspring.webtestmodule.example.model.intercept.PersonCopyIntercept;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DataMappingInfo(customProcessor = PersonCopyIntercept.class)
public class PersonDetailDTO extends PersonMasterDTO {

    public String address;
    public String email;
    public String occupation;

}
