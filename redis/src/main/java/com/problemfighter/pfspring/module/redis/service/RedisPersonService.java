package com.problemfighter.pfspring.module.redis.service;

import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonDetailDTO;
import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonMasterDTO;
import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonUpdateDTO;
import com.problemfighter.pfspring.module.redis.model.entity.RedisPerson;
import com.problemfighter.pfspring.module.redis.repository.RedisPersonRepository;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RedisPersonService implements RequestResponse, RestApiAction<RedisPersonMasterDTO, RedisPersonDetailDTO, RedisPersonUpdateDTO> {

    @Autowired
    private RedisPersonRepository personRepository;

    @Autowired
    private CacheManagerService cacheManagerService;

    @Override
    @Cacheable("redis_person_list")
    public MessageResponse create(RequestData<RedisPersonDetailDTO> data) {
        RedisPerson entity = requestProcessor().process(data, RedisPerson.class);
        personRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @Override
    public BulkResponse<RedisPersonDetailDTO> bulkCreate(RequestBulkData<RedisPersonDetailDTO> data) {
        BulkErrorValidEntities<RedisPersonDetailDTO, RedisPerson> bulkData = requestProcessor().process(data, RedisPerson.class);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, RedisPersonDetailDTO.class);
    }

    @Override
    @Cacheable("redis_person_list")
    public PageableResponse<RedisPersonMasterDTO> list(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), RedisPersonMasterDTO.class);
    }

    @Override
    public PageableResponse<RedisPersonDetailDTO> detailList(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), false), RedisPersonDetailDTO.class);
    }

    @Override
    public PageableResponse<RedisPersonMasterDTO> trash(Integer page, Integer size, String sort, String field, String search) {
        return responseProcessor().response(personRepository.list(requestProcessor().paginationNSort(page, size, sort, field), true), RedisPersonMasterDTO.class);
    }

    @Override
    public DetailsResponse<RedisPersonDetailDTO> details(Long id) {
        return responseProcessor().response(personRepository.findById(id), RedisPersonDetailDTO.class, "Item not found");
    }

    @Override
    @CacheEvict(value = "redis_person_list", allEntries = true)
    public MessageResponse update(RequestData<RedisPersonUpdateDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        RedisPerson entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        entity = requestProcessor().process(data, entity);
        personRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @Override
    public BulkResponse<RedisPersonUpdateDTO> bulkUpdate(RequestBulkData<RedisPersonUpdateDTO> data) {
        Iterable<RedisPerson> entities = personRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<RedisPersonUpdateDTO, RedisPerson> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            personRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, RedisPersonUpdateDTO.class);
    }

    @Override
    public MessageResponse bulkDelete(RequestBulkData<Long> data) {
        Iterable<RedisPerson> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsDeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse hardDelete(RequestBulkData<Long> data) {
        Iterable<RedisPerson> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        personRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse delete(Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        RedisPerson entity = dataUtil().validateAndOptionToEntity(personRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        personRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @Override
    public MessageResponse bulkRestore(RequestBulkData<Long> data) {
        Iterable<RedisPerson> entities = personRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsUndeleted(entities);
        personRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }

    public Boolean isEmailAlreadyExist(String email) {
        return personRepository.findByEmail(email) != null;
    }

    public RedisPerson findByEmailAndId(String email, Long id) {
        return personRepository.findByEmailAndId(email, id);
    }

    public MessageResponse clearAllCache() {
        cacheManagerService.cleanAllCache();
        return responseProcessor().response("Cache Clear");
    }
}
