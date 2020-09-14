package com.problemfighter.pfspring.webtestmodule.example.service;

import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonDetailDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonMasterDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonUpdateDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.entity.Person;
import com.problemfighter.pfspring.webtestmodule.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements RequestResponse, RestApiAction<PersonMasterDTO, PersonDetailDTO, PersonUpdateDTO> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public MessageResponse create(RequestData<PersonDetailDTO> data) {
        Person entity = requestProcessor().process(data, Person.class);
        personRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @Override
    public BulkResponse<PersonDetailDTO> bulkCreate(RequestBulkData<PersonDetailDTO> data) {
        BulkErrorValidEntities<PersonDetailDTO, Person> bulkData = requestProcessor().process(data, Person.class);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, PersonDetailDTO.class);
    }

    @Override
    public PageableResponse<PersonMasterDTO> list(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), PersonMasterDTO.class);
    }

    @Override
    public PageableResponse<PersonDetailDTO> detailList(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), PersonDetailDTO.class);
    }

    @Override
    public PageableResponse<PersonMasterDTO> trash(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), true), PersonMasterDTO.class);
    }

    @Override
    public DetailsResponse<PersonDetailDTO> details(Long id) {
        return responseProcessor().response(personRepository.findById(id), PersonDetailDTO.class, "Item not found");
    }

    @Override
    public MessageResponse update(RequestData<PersonUpdateDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        Person entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        personRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @Override
    public BulkResponse<PersonUpdateDTO> bulkUpdate(RequestBulkData<PersonUpdateDTO> data) {
        Iterable<Person> entities = personRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<PersonUpdateDTO, Person> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, PersonUpdateDTO.class);
    }

    @Override
    public MessageResponse bulkDelete(RequestBulkData<Long> data) {
        Iterable<Person> entities = personRepository.findAllById(data.getData());
        dataUtil().markAsDeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse hardDelete(RequestBulkData<Long> data) {
        Iterable<Person> entities = personRepository.findAllById(data.getData());
        personRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse delete(Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        Person entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        personRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse bulkRestore(RequestBulkData<Long> data) {
        Iterable<Person> entities = personRepository.findAllById(data.getData());
        dataUtil().markAsUndeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }
}
