package com.problemfighter.pfspring.webtestmodule.controller.api;


import com.problemfighter.pfspring.restapi.inter.MethodStructure;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentDetailDTO;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentMasterDTO;
import com.problemfighter.pfspring.webtestmodule.model.dto.department.DepartmentUpdateDTO;
import com.problemfighter.pfspring.webtestmodule.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/department")
public class ApiV1DepartmentController implements MethodStructure<DepartmentMasterDTO, DepartmentDetailDTO, DepartmentUpdateDTO> {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<DepartmentDetailDTO> data) {
        return departmentService.create(data);
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<DepartmentDetailDTO> bulkCreate(@RequestBody RequestBulkData<DepartmentDetailDTO> data) {
        return departmentService.bulkCreate(data);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<DepartmentMasterDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return departmentService.list(page, size, sort, field, search);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<DepartmentDetailDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return departmentService.detailList(page, size, sort, field, search);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<DepartmentMasterDTO> trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return departmentService.trash(page, size, sort, field, search);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<DepartmentDetailDTO> details(@PathVariable(name = "id") Long id) {
        return departmentService.details(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<DepartmentUpdateDTO> data) {
        return departmentService.update(data);
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<DepartmentUpdateDTO> bulkUpdate(@RequestBody RequestBulkData<DepartmentUpdateDTO> data) {
        return departmentService.bulkUpdate(data);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> ids) {
        return departmentService.bulkDelete(ids);
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> ids) {
        return departmentService.hardDelete(ids);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        return departmentService.delete(id);
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> ids) {
        return departmentService.bulkRestore(ids);
    }
}
