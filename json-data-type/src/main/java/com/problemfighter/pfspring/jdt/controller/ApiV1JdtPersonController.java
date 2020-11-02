package com.problemfighter.pfspring.jdt.controller;



import com.problemfighter.pfspring.jdt.model.dto.person.JdtJdtPersonDetailDTO;
import com.problemfighter.pfspring.jdt.model.dto.person.JdtPersonMasterDTO;
import com.problemfighter.pfspring.jdt.model.dto.person.JdtJdtPersonUpdateDTO;
import com.problemfighter.pfspring.jdt.service.JdtPersonService;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jdt-person")
public class ApiV1JdtPersonController implements RestApiAction<JdtPersonMasterDTO, JdtJdtPersonDetailDTO, JdtJdtPersonUpdateDTO> {

    @Autowired
    private JdtPersonService jdtPersonService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<JdtJdtPersonDetailDTO> data) {
        return jdtPersonService.create(data);
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<JdtJdtPersonDetailDTO> bulkCreate(@RequestBody RequestBulkData<JdtJdtPersonDetailDTO> data) {
        return jdtPersonService.bulkCreate(data);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<JdtPersonMasterDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return jdtPersonService.list(page, size, sort, field, search);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<JdtJdtPersonDetailDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    )  {
        return jdtPersonService.detailList(page, size, sort, field, search);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<JdtPersonMasterDTO>  trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return jdtPersonService.trash(page, size, sort, field, search);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<JdtJdtPersonDetailDTO> details(@PathVariable(name = "id") Long id) {
        return jdtPersonService.details(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<JdtJdtPersonUpdateDTO> data) {
        return jdtPersonService.update(data);
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<JdtJdtPersonUpdateDTO> bulkUpdate(@RequestBody RequestBulkData<JdtJdtPersonUpdateDTO> data) {
        return jdtPersonService.bulkUpdate(data);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> data) {
        return jdtPersonService.bulkDelete(data);
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> data) {
        return jdtPersonService.hardDelete(data);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        return jdtPersonService.delete(id);
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> data) {
        return jdtPersonService.bulkRestore(data);
    }
}
