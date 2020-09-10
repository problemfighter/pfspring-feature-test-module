package com.problemfighter.pfspring.webtestmodule.service;


import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.webtestmodule.model.dto.student.StudentDetailDTO;
import com.problemfighter.pfspring.webtestmodule.model.entity.Student;
import com.problemfighter.pfspring.webtestmodule.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements RequestResponse {


    @Autowired
    private StudentRepository studentRepository;


    public MessageResponse save(RequestData<StudentDetailDTO> data) {
        Student student = requestProcessor().process(data, Student.class);
        studentRepository.save(student);
        return responseProcessor().successMessage("Created");
    }

}
