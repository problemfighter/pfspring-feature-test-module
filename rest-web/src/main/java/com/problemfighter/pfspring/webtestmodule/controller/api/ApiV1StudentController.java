package com.problemfighter.pfspring.webtestmodule.controller.api;


import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import com.problemfighter.pfspring.webtestmodule.model.dto.student.StudentDetailDTO;
import com.problemfighter.pfspring.webtestmodule.model.dto.student.StudentMasterDTO;
import com.problemfighter.pfspring.webtestmodule.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/student")
public class ApiV1StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<StudentDetailDTO> data) {
        return studentService.save(data);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<StudentMasterDTO> data) {
        return null;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<StudentDetailDTO> list() {
        return null;
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<StudentMasterDTO> details(@PathVariable(name = "id") Long id) {
        return null;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        return null;
    }

}
