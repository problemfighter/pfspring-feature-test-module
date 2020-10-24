package com.problemfighter.pfspring.hl2c.service;

import com.problemfighter.pfspring.hl2c.model.dto.person.Hl2cPersonDetailDTO;
import com.problemfighter.pfspring.hl2c.model.dto.person.Hl2cPersonMasterDTO;
import com.problemfighter.pfspring.hl2c.model.dto.person.Hl2cPersonUpdateDTO;
import com.problemfighter.pfspring.hl2c.model.entity.Hl2cPerson;
import com.problemfighter.pfspring.hl2c.repository.Hl2cPersonRepository;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Hl2cPersonService implements RequestResponse, RestApiAction<Hl2cPersonMasterDTO, Hl2cPersonDetailDTO, Hl2cPersonUpdateDTO> {

    @Autowired
    private Hl2cPersonRepository personRepository;


    @Override
    public MessageResponse create(RequestData<Hl2cPersonDetailDTO> data) {
        Hl2cPerson entity = requestProcessor().process(data, Hl2cPerson.class);
        personRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @Override
    public BulkResponse<Hl2cPersonDetailDTO> bulkCreate(RequestBulkData<Hl2cPersonDetailDTO> data) {
        BulkErrorValidEntities<Hl2cPersonDetailDTO, Hl2cPerson> bulkData = requestProcessor().process(data, Hl2cPerson.class);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, Hl2cPersonDetailDTO.class);
    }

    @Override
    public PageableResponse<Hl2cPersonMasterDTO> list(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), Hl2cPersonMasterDTO.class);
    }

    @Override
    public PageableResponse<Hl2cPersonDetailDTO> detailList(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), Hl2cPersonDetailDTO.class);
    }

    @Override
    public PageableResponse<Hl2cPersonMasterDTO> trash(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), true), Hl2cPersonMasterDTO.class);
    }

    @Override
    public DetailsResponse<Hl2cPersonDetailDTO> details(Long id) {
        return responseProcessor().response(personRepository.findById(id), Hl2cPersonDetailDTO.class, "Item not found");
    }

    @Override
    public MessageResponse update(RequestData<Hl2cPersonUpdateDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        Hl2cPerson entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        entity = requestProcessor().process(data, entity);
        personRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @Override
    public BulkResponse<Hl2cPersonUpdateDTO> bulkUpdate(RequestBulkData<Hl2cPersonUpdateDTO> data) {
        Iterable<Hl2cPerson> entities = personRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<Hl2cPersonUpdateDTO, Hl2cPerson> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, Hl2cPersonUpdateDTO.class);
    }

    @Override
    public MessageResponse bulkDelete(RequestBulkData<Long> data) {
        Iterable<Hl2cPerson> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsDeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse hardDelete(RequestBulkData<Long> data) {
        Iterable<Hl2cPerson> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        personRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse delete(Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        Hl2cPerson entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        personRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse bulkRestore(RequestBulkData<Long> data) {
        Iterable<Hl2cPerson> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsUndeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }

    public Boolean isEmailAlreadyExist(String email) {
        return personRepository.findByEmail(email) != null;
    }

    public Hl2cPerson findByEmailAndId(String email, Long id) {
        return personRepository.findByEmailAndId(email, id);
    }

}
