package com.problemfighter.pfspring.webtestmodule.example.model.intercept;

import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.inter.CopyInterceptor;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonDetailDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.dto.person.PersonUpdateDTO;
import com.problemfighter.pfspring.webtestmodule.example.model.entity.Person;
import com.problemfighter.pfspring.webtestmodule.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonCopyIntercept implements CopyInterceptor<Person, PersonDetailDTO, PersonUpdateDTO> {

    @Autowired
    private PersonService personService;


    private void checkEmailAlreadyExist(String email) throws ApiRestException {
        if (email != null && personService.isEmailAlreadyExist(email)) {
            ApiRestException.otherError("Email already exist");
        }
    }

    @Override
    public void meAsSrc(PersonUpdateDTO source, Person destination) {
        if (personService.findByEmailAndId(source.email, source.id) == null) {
            checkEmailAlreadyExist(source.email);
        }
    }

    @Override
    public void meAsDst(Person source, PersonUpdateDTO destination) {

    }

    @Override
    public void meAsSrc(PersonDetailDTO source, Person destination) {
        checkEmailAlreadyExist(source.email);
    }

    @Override
    public void meAsDst(Person source, PersonDetailDTO destination) {

    }
}