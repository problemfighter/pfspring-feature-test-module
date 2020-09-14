package com.problemfighter.pfspring.webtestmodule.example.model.dto.person;

import com.problemfighter.java.oc.annotation.DataMappingInfo;
import com.problemfighter.pfspring.webtestmodule.example.model.intercept.PersonCopyIntercept;

@DataMappingInfo(customProcessor = PersonCopyIntercept.class)
public class PersonDetailDTO extends PersonMasterDTO {

    public String address;
    public String email;
    public String occupation;

}
