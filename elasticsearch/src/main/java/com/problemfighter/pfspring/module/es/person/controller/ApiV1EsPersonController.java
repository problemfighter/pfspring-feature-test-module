package com.problemfighter.pfspring.module.es.person.controller;


import com.problemfighter.pfspring.module.es.person.model.entity.EsPerson;
import com.problemfighter.pfspring.module.es.person.service.EsPersonService;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @RequestMapping(value = "/find-by-sex", method = RequestMethod.GET)
    public DetailsListResponse<EsPerson> findBySex(
            @RequestParam(value = "sex", defaultValue = "Male") String sex
    ) {
        return esPersonService.findBySex(sex);
    }

    @RequestMapping(value = "/find-by-sex-in-list", method = RequestMethod.POST)
    public DetailsListResponse<EsPerson> findBySex(@RequestBody RequestBulkData<String> data) {
        return esPersonService.findBySex(data.getData());
    }

    @RequestMapping(value = "/find-by-sex-not-in-list", method = RequestMethod.POST)
    public DetailsListResponse<EsPerson> findBySexNotInList(@RequestBody RequestBulkData<String> data) {
        return esPersonService.findByNotEqualSex(data.getData());
    }

    @RequestMapping(value = "/income-sum", method = RequestMethod.GET)
    public Double incomeSum() {
        return esPersonService.sumOfTotalIncome();
    }

    @RequestMapping(value = "/income-max", method = RequestMethod.GET)
    public Double incomeMax() {
        return esPersonService.maxIncome();
    }

    @RequestMapping(value = "/income-avg", method = RequestMethod.GET)
    public Double incomeAvg() {
        return esPersonService.averageIncome();
    }

    @RequestMapping(value = "/income-max-by-occupation", method = RequestMethod.GET)
    public Double maxIncomeByOccupation(@RequestParam(value = "occupation", defaultValue = "Software Engineer") String occupation) {
        return esPersonService.maxIncomeByOccupation(occupation);
    }

    @RequestMapping(value = "/by-age-range", method = RequestMethod.GET)
    public DetailsListResponse<EsPerson> findByAgeRange(@RequestParam(value = "start", defaultValue = "20") Double start, @RequestParam(value = "end", defaultValue = "25") Double end) {
        return esPersonService.findByAgeRange(start, end);
    }

    @RequestMapping(value = "/group-by-occupation", method = RequestMethod.GET)
    public Map<String,Object> groupByOccupation() {
        return esPersonService.groupByOccupation();
    }

    @RequestMapping(value = "/distinct-occupation", method = RequestMethod.GET)
    public Long distinctOccupation() {
        return esPersonService.distinctOccupation();
    }

    @RequestMapping(value = "/stats-aggregation", method = RequestMethod.GET)
    public Map<String,Object> statsAggregation() {
        return esPersonService.statsAggregation();
    }

}
