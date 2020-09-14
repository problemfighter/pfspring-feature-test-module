package com.problemfighter.pfspring.webtestmodule.example.controller;


import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonDetailDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonMasterDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonUpdateDTO;
import com.problemfighter.pfspring.webtestmodule.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
public class ApiV1PersonController implements RestApiAction<PersonMasterDTO, PersonDetailDTO, PersonUpdateDTO> {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<PersonDetailDTO> data) {
        return personService.create(data);
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<PersonDetailDTO> bulkCreate(@RequestBody RequestBulkData<PersonDetailDTO> data) {
        return personService.bulkCreate(data);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<PersonMasterDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return personService.list(page, size, sort, field, search);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<PersonDetailDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    )  {
        return personService.detailList(page, size, sort, field, search);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<PersonMasterDTO>  trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return personService.trash(page, size, sort, field, search);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<PersonDetailDTO> details(@PathVariable(name = "id") Long id) {
        return personService.details(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<PersonUpdateDTO> data) {
        return personService.update(data);
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<PersonUpdateDTO> bulkUpdate(@RequestBody RequestBulkData<PersonUpdateDTO> data) {
        return personService.bulkUpdate(data);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> data) {
        return personService.bulkDelete(data);
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> data) {
        return personService.hardDelete(data);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        return personService.delete(id);
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> data) {
        return personService.bulkRestore(data);
    }
}
