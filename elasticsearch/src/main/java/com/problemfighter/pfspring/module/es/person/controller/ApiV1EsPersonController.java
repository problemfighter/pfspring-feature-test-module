package com.problemfighter.pfspring.module.es.person.controller;


import com.problemfighter.pfspring.module.es.person.model.entity.EsPerson;
import com.problemfighter.pfspring.module.es.person.service.EsPersonService;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/es/v1/person")
public class ApiV1EsPersonController {

    @Autowired
    private EsPersonService esPersonService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<EsPerson> list(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5000") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "name") String field
    ) {
        return esPersonService.list(page, size, sort, field);
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<EsPerson> bulkCreate(@RequestBody RequestBulkData<EsPerson> data) {
        return esPersonService.bulkCreate(data);
    }
}
