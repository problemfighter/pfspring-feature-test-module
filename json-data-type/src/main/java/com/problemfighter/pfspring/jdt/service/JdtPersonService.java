package com.problemfighter.pfspring.jdt.service;

import com.problemfighter.pfspring.jdt.model.dto.person.JdtJdtPersonDetailDTO;
import com.problemfighter.pfspring.jdt.model.dto.person.JdtPersonMasterDTO;
import com.problemfighter.pfspring.jdt.model.dto.person.JdtJdtPersonUpdateDTO;
import com.problemfighter.pfspring.jdt.model.entity.JdtPerson;
import com.problemfighter.pfspring.jdt.repository.JdtPersonRepository;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdtPersonService implements RequestResponse, RestApiAction<JdtPersonMasterDTO, JdtJdtPersonDetailDTO, JdtJdtPersonUpdateDTO> {

    @Autowired
    private JdtPersonRepository jdtPersonRepository;

    @Override
    public MessageResponse create(RequestData<JdtJdtPersonDetailDTO> data) {
        JdtPerson entity = requestProcessor().process(data, JdtPerson.class);
        jdtPersonRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @Override
    public BulkResponse<JdtJdtPersonDetailDTO> bulkCreate(RequestBulkData<JdtJdtPersonDetailDTO> data) {
        BulkErrorValidEntities<JdtJdtPersonDetailDTO, JdtPerson> bulkData = requestProcessor().process(data, JdtPerson.class);
        if (bulkData.isValidEntities()) {
            jdtPersonRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, JdtJdtPersonDetailDTO.class);
    }

    @Override
    public PageableResponse<JdtPersonMasterDTO> list(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(jdtPersonRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), JdtPersonMasterDTO.class);
    }

    @Override
    public PageableResponse<JdtJdtPersonDetailDTO> detailList(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(jdtPersonRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), JdtJdtPersonDetailDTO.class);
    }

    @Override
    public PageableResponse<JdtPersonMasterDTO> trash(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(jdtPersonRepository.list(requestProcessor().paginationNSort(page, size, sort, field), true), JdtPersonMasterDTO.class);
    }

    @Override
    public DetailsResponse<JdtJdtPersonDetailDTO> details(Long id) {
        return responseProcessor().response(jdtPersonRepository.findById(id), JdtJdtPersonDetailDTO.class, "Item not found");
    }

    @Override
    public MessageResponse update(RequestData<JdtJdtPersonUpdateDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        JdtPerson entity = dataUtil().validateAndOptionToEntity(jdtPersonRepository.findById(id), "Content not found");
        jdtPersonRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @Override
    public BulkResponse<JdtJdtPersonUpdateDTO> bulkUpdate(RequestBulkData<JdtJdtPersonUpdateDTO> data) {
        Iterable<JdtPerson> entities = jdtPersonRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<JdtJdtPersonUpdateDTO, JdtPerson> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            jdtPersonRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, JdtJdtPersonUpdateDTO.class);
    }

    @Override
    public MessageResponse bulkDelete(RequestBulkData<Long> data) {
        Iterable<JdtPerson> entities = jdtPersonRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsDeleted(entities);
        jdtPersonRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse hardDelete(RequestBulkData<Long> data) {
        Iterable<JdtPerson> entities = jdtPersonRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        jdtPersonRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse delete(Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        JdtPerson entity = dataUtil().validateAndOptionToEntity(jdtPersonRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        jdtPersonRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse bulkRestore(RequestBulkData<Long> data) {
        Iterable<JdtPerson> entities = jdtPersonRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsUndeleted(entities);
        jdtPersonRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }

    public Boolean isEmailAlreadyExist(String email) {
        return jdtPersonRepository.findByEmail(email) != null;
    }

    public JdtPerson findByEmailAndId(String email, Long id) {
        return jdtPersonRepository.findByEmailAndId(email, id);
    }
}
