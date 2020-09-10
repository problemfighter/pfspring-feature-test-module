package com.problemfighter.pfspring.webtestmodule.model.dto.department;


import com.problemfighter.java.oc.annotation.DataMappingInfo;
import com.problemfighter.pfspring.webtestmodule.model.intercept.DepartmentIntercept;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Department Details DTO")
@DataMappingInfo(customProcessor = DepartmentIntercept.class)
public class DepartmentDetailDTO extends DepartmentMasterDTO {
}
