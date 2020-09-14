package com.problemfighter.pfspring.webtestmodule.service;

import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentDetailDTO;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentMasterDTO;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentUpdateDTO;
import com.problemfighter.pfspring.webtestmodule.model.entity.Department;
import com.problemfighter.pfspring.webtestmodule.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentService implements RequestResponse, RestApiAction<DepartmentMasterDTO, DepartmentDetailDTO, DepartmentUpdateDTO> {


    @Autowired
    private DepartmentRepository departmentRepository;

    public Boolean isCodeAlreadyExist(String code) {
        Department department = departmentRepository.findDepartmentByCode(code);
        if (department == null) {
            return false;
        }
        return true;
    }

    public Department findByIdAndCode(String code, Long id) {
        return departmentRepository.findDepartmentByCodeAndId(code, id);
    }

    @Override
    public MessageResponse create(RequestData<DepartmentDetailDTO> data) {
        Department department = requestProcessor().process(data, Department.class);
        departmentRepository.save(department);
        return responseProcessor().response("Created");
    }


    @Override
    public PageableResponse<DepartmentMasterDTO> list(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(departmentRepository.list(
                requestProcessor().paginationNSort(page, size, sort, field), false),
                DepartmentMasterDTO.class);
    }

    public PageableResponse<DepartmentMasterDTO> trash(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(departmentRepository.list(requestProcessor().paginationNSort(page, size, sort, field), true), DepartmentMasterDTO.class);
    }

    @Override
    public PageableResponse<DepartmentDetailDTO> detailList(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(departmentRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), DepartmentDetailDTO.class);
    }

    @Override
    public DetailsResponse<DepartmentDetailDTO> details(Long id) {
        return responseProcessor().response(departmentRepository.findById(id), DepartmentDetailDTO.class, "Item not found");
    }

    public Department validateAndGetDepartmentById(Long id) {
        Optional<Department> optional = departmentRepository.findById(requestProcessor().validateId(id, "Id not found"));
        return dataUtil().validateAndOptionToEntity(optional, "Content not found");
    }

    @Override
    public MessageResponse update(RequestData<DepartmentUpdateDTO> data) {
        Department department = validateAndGetDepartmentById(requestProcessor().getId(data));
        department = requestProcessor().process(data, department);
        departmentRepository.save(department);
        return responseProcessor().response("Updated");
    }

    @Override
    public BulkResponse<DepartmentDetailDTO> bulkCreate(RequestBulkData<DepartmentDetailDTO> data) {
        BulkErrorValidEntities<DepartmentDetailDTO, Department> bulkData = requestProcessor().process(data, Department.class);
        if (bulkData.isValidEntities()) {
            departmentRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, DepartmentDetailDTO.class);
    }

    public Iterable<Department> getAllByIds(List<Long> ids) {
        return departmentRepository.findAllById(ids);
    }

    @Override
    public BulkResponse<DepartmentUpdateDTO> bulkUpdate(RequestBulkData<DepartmentUpdateDTO> data) {
        Iterable<Department> departments = getAllByIds(dataUtil().getAllId(data));
        BulkErrorValidEntities<DepartmentUpdateDTO, Department> bulkErrorValidEntities = dataUtil().merge(departments, data);
        departmentRepository.saveAll(bulkErrorValidEntities.entityList);
        return responseProcessor().response(bulkErrorValidEntities, DepartmentUpdateDTO.class);
    }

    @Override
    public MessageResponse bulkDelete(RequestBulkData<Long> data) {
        Iterable<Department> departmentList = departmentRepository.findAllById(data.getData());
        dataUtil().markAsDeleted(departmentList);
        departmentRepository.saveAll(departmentList);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse hardDelete(RequestBulkData<Long> ids) {
        Iterable<Department> departments = getAllByIds(ids.getData());
        departmentRepository.deleteAll(departments);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse delete(Long id) {
        Department department = validateAndGetDepartmentById(id);
        dataUtil().markAsDeleted(department);
        departmentRepository.save(department);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse bulkRestore(RequestBulkData<Long> ids) {
        Iterable<Department> departmentList = departmentRepository.findAllById(ids.getData());
        if (dataUtil().isEmpty(departmentList)) {
            return responseProcessor().error("No data found!");
        }
        dataUtil().markAsUndeleted(departmentList);
        departmentRepository.saveAll(departmentList);
        return responseProcessor().response("Restored");
    }
}
