package com.problemfighter.pfspring.webtestmodule.model.dto.department;

import com.problemfighter.pfspring.common.model.DTOCommon;
import com.problemfighter.pfspring.restapi.inter.model.RestDTO;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;


@ApiModel(description = "Department Master DTO")
public class DepartmentMasterDTO extends DTOCommon implements RestDTO {

    @NotNull(message = "Please enter department name")
    public String name;

    @NotNull(message = "Please enter department code")
    public String code;
    public String description;
}
