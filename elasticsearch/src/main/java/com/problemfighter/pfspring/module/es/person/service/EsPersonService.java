package com.problemfighter.pfspring.module.es.person.service;

import com.problemfighter.pfspring.module.es.person.model.entity.EsPerson;
import com.problemfighter.pfspring.module.es.person.repository.EsPersonRepository;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.response.BulkErrorValidEntities;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EsPersonService implements RequestResponse {

    @Autowired
    private EsPersonRepository esPersonRepository;

    public PageableResponse<EsPerson> list(Integer page, Integer size, String sort, String field) {
        return responseProcessor().response(esPersonRepository.findAll(PageRequest.of(page, size)), EsPerson.class);
    }

    public BulkResponse<EsPerson> bulkCreate(RequestBulkData<EsPerson> data) {
        BulkErrorValidEntities<EsPerson, EsPerson> bulkData = requestProcessor().process(data, EsPerson.class);
        if (bulkData.isValidEntities()) {
            esPersonRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, EsPerson.class);
    }

}
