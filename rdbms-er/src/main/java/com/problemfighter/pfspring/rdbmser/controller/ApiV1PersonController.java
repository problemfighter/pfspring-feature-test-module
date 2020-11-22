package com.problemfighter.pfspring.rdbmser.controller;


import com.problemfighter.pfspring.rdbmser.model.dto.PersonDTO;
import com.problemfighter.pfspring.rdbmser.model.entity.Person;
import com.problemfighter.pfspring.rdbmser.repository.PersonRepository;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
public class ApiV1PersonController implements RequestResponse, RestApiAction<PersonDTO, PersonDTO, PersonDTO> {
    
    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<PersonDTO> data) {
        Person entity = requestProcessor().process(data, Person.class);
        personRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<PersonDTO> bulkCreate(@RequestBody RequestBulkData<PersonDTO> data) {
        BulkErrorValidEntities<PersonDTO, Person> bulkData = requestProcessor().process(data, Person.class);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, PersonDTO.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<PersonDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), PersonDTO.class);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<PersonDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    )  {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), PersonDTO.class);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<PersonDTO>  trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), PersonDTO.class);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<PersonDTO> details(@PathVariable(name = "id") Long id) {
        return responseProcessor().response(personRepository.findById(id), PersonDTO.class, "Item not found");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<PersonDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        Person entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        personRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<PersonDTO> bulkUpdate(@RequestBody RequestBulkData<PersonDTO> data) {
        Iterable<Person> entities = personRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<PersonDTO, Person> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, PersonDTO.class);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> data) {
        Iterable<Person> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsDeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> data) {
        Iterable<Person> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        personRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        Person entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        personRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> data) {
        Iterable<Person> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsUndeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }
}
