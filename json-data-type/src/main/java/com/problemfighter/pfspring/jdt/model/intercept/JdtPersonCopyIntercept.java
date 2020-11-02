package com.problemfighter.pfspring.jdt.model.intercept;

import com.problemfighter.pfspring.jdt.model.dto.person.JdtJdtPersonDetailDTO;
import com.problemfighter.pfspring.jdt.model.dto.person.JdtJdtPersonUpdateDTO;
import com.problemfighter.pfspring.jdt.model.entity.JdtPerson;
import com.problemfighter.pfspring.jdt.service.JdtPersonService;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.inter.CopyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdtPersonCopyIntercept implements CopyInterceptor<JdtPerson, JdtJdtPersonDetailDTO, JdtJdtPersonUpdateDTO> {

    @Autowired
    private JdtPersonService jdtPersonService;


    private void checkEmailAlreadyExist(String email) throws ApiRestException {
        if (email != null && jdtPersonService.isEmailAlreadyExist(email)) {
            ApiRestException.otherError("Email already exist");
        }
    }

    @Override
    public void meAsSrc(JdtJdtPersonUpdateDTO source, JdtPerson destination) {
        if (jdtPersonService.findByEmailAndId(source.email, source.id) == null) {
            checkEmailAlreadyExist(source.email);
        }
    }

    @Override
    public void meAsDst(JdtPerson source, JdtJdtPersonUpdateDTO destination) {

    }

    @Override
    public void meAsSrc(JdtJdtPersonDetailDTO source, JdtPerson destination) {
        checkEmailAlreadyExist(source.email);
    }

    @Override
    public void meAsDst(JdtPerson source, JdtJdtPersonDetailDTO destination) {

    }
}
