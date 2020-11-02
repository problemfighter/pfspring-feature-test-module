package com.problemfighter.pfspring.jdt.model.dto.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.problemfighter.java.oc.annotation.DataMappingInfo;
import com.problemfighter.pfspring.jdt.model.intercept.JdtPersonCopyIntercept;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DataMappingInfo(customProcessor = JdtPersonCopyIntercept.class)
public class JdtJdtPersonDetailDTO extends JdtPersonMasterDTO {

    public String address;
    public String email;
    public String occupation;

}
