package com.problemfighter.pfspring.module.es.controller;



import com.problemfighter.pfspring.module.es.model.entity.EsDepartment;
import com.problemfighter.pfspring.module.es.repository.EsDepartmentRepository;
import com.problemfighter.pfspring.module.es.service.EsDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/esdepartment")
public class ApiV1EsDepartmentController {

    @Autowired
    private EsDepartmentRepository esDepartmentRepository;

    @Autowired
    private EsDepartmentService esDepartmentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Iterable<EsDepartment> list() {
        return esDepartmentRepository.findAll();
    }

    @RequestMapping(value = "/list-custom", method = RequestMethod.GET)
    public Object listCustom() {
        return esDepartmentService.getAll("engineering");
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object count() {
        esDepartmentService.countCse();
        return null;
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public String bulkCreate(@RequestBody List<EsDepartment> data) {
        esDepartmentRepository.saveAll(data);
        return "Saved";
    }

}
