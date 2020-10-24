package com.problemfighter.pfspring.hl2c.model.intercept;

import com.problemfighter.pfspring.hl2c.model.dto.person.Hl2cPersonDetailDTO;
import com.problemfighter.pfspring.hl2c.model.dto.person.Hl2cPersonUpdateDTO;
import com.problemfighter.pfspring.hl2c.model.entity.Hl2cPerson;
import com.problemfighter.pfspring.hl2c.service.Hl2cPersonService;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.inter.CopyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Hl2cPersonCopyIntercept implements CopyInterceptor<Hl2cPerson, Hl2cPersonDetailDTO, Hl2cPersonUpdateDTO> {

    @Autowired
    private Hl2cPersonService personService;


    private void checkEmailAlreadyExist(String email) throws ApiRestException {
        if (email != null && personService.isEmailAlreadyExist(email)) {
            ApiRestException.otherError("Email already exist");
        }
    }

    @Override
    public void meAsSrc(Hl2cPersonUpdateDTO source, Hl2cPerson destination) {
        if (personService.findByEmailAndId(source.email, source.id) == null) {
            checkEmailAlreadyExist(source.email);
        }
    }

    @Override
    public void meAsDst(Hl2cPerson source, Hl2cPersonUpdateDTO destination) {

    }

    @Override
    public void meAsSrc(Hl2cPersonDetailDTO source, Hl2cPerson destination) {
        checkEmailAlreadyExist(source.email);
    }

    @Override
    public void meAsDst(Hl2cPerson source, Hl2cPersonDetailDTO destination) {

    }
}
