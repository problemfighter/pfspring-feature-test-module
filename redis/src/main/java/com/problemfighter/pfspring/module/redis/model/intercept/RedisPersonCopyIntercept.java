package com.problemfighter.pfspring.module.redis.model.intercept;

import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonDetailDTO;
import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonUpdateDTO;
import com.problemfighter.pfspring.module.redis.model.entity.RedisPerson;
import com.problemfighter.pfspring.module.redis.service.RedisPersonService;
import com.problemfighter.pfspring.restapi.common.ApiRestException;
import com.problemfighter.pfspring.restapi.inter.CopyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisPersonCopyIntercept implements CopyInterceptor<RedisPerson, RedisPersonDetailDTO, RedisPersonUpdateDTO> {

    @Autowired
    private RedisPersonService personService;


    private void checkEmailAlreadyExist(String email) throws ApiRestException {
        if (email != null && personService.isEmailAlreadyExist(email)) {
            ApiRestException.otherError("Email already exist");
        }
    }

    @Override
    public void meAsSrc(RedisPersonUpdateDTO source, RedisPerson destination) {
        if (personService.findByEmailAndId(source.email, source.id) == null) {
            checkEmailAlreadyExist(source.email);
        }
    }

    @Override
    public void meAsDst(RedisPerson source, RedisPersonUpdateDTO destination) {

    }

    @Override
    public void meAsSrc(RedisPersonDetailDTO source, RedisPerson destination) {
        checkEmailAlreadyExist(source.email);
    }

    @Override
    public void meAsDst(RedisPerson source, RedisPersonDetailDTO destination) {

    }
}
