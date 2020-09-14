package com.problemfighter.pfspring.webtestmodule.model.intercept;

import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.inter.CopyInterceptor;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentDetailDTO;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentUpdateDTO;
import com.problemfighter.pfspring.webtestmodule.model.entity.Department;
import com.problemfighter.pfspring.webtestmodule.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentIntercept implements CopyInterceptor<Department, DepartmentDetailDTO, DepartmentUpdateDTO> {

    @Autowired
    private DepartmentService departmentService;


    private void chekCodeAlreadyExist(String code) throws ApiRestException {
        if (code != null && departmentService.isCodeAlreadyExist(code)) {
            ApiRestException.otherError("Department code already exist");
        }
    }

    @Override
    public void meAsSrc(DepartmentDetailDTO source, Department destination) {
        chekCodeAlreadyExist(source.code);
    }

    @Override
    public void meAsDst(Department source, DepartmentDetailDTO destination) {
        System.out.println("======= meAsDst ED =======");
    }

    @Override
    public void meAsSrc(DepartmentUpdateDTO source, Department destination) {
        if (departmentService.findByIdAndCode(source.code, source.id) == null) {
            chekCodeAlreadyExist(source.code);
        }
        System.out.println("======= meAsSrc UE =======");
    }

    @Override
    public void meAsDst(Department source, DepartmentUpdateDTO destination) {
        System.out.println("======= meAsDst EU =======");
    }
}
