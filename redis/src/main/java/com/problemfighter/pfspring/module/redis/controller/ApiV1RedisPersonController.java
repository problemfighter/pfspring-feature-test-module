package com.problemfighter.pfspring.module.redis.controller;


import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonDetailDTO;
import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonMasterDTO;
import com.problemfighter.pfspring.module.redis.model.dto.person.RedisPersonUpdateDTO;
import com.problemfighter.pfspring.module.redis.service.RedisPersonService;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis-person")
public class ApiV1RedisPersonController implements RestApiAction<RedisPersonMasterDTO, RedisPersonDetailDTO, RedisPersonUpdateDTO> {

    @Autowired
    private RedisPersonService personService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<RedisPersonDetailDTO> data) {
        return personService.create(data);
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<RedisPersonDetailDTO> bulkCreate(@RequestBody RequestBulkData<RedisPersonDetailDTO> data) {
        return personService.bulkCreate(data);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<RedisPersonMasterDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return personService.list(page, size, sort, field, search);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<RedisPersonDetailDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    )  {
        return personService.detailList(page, size, sort, field, search);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<RedisPersonMasterDTO>  trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return personService.trash(page, size, sort, field, search);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<RedisPersonDetailDTO> details(@PathVariable(name = "id") Long id) {
        return personService.details(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<RedisPersonUpdateDTO> data) {
        return personService.update(data);
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<RedisPersonUpdateDTO> bulkUpdate(@RequestBody RequestBulkData<RedisPersonUpdateDTO> data) {
        return personService.bulkUpdate(data);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> data) {
        return personService.bulkDelete(data);
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> data) {
        return personService.hardDelete(data);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        return personService.delete(id);
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> data) {
        return personService.bulkRestore(data);
    }

    @RequestMapping(value = "/clear-cache", method = RequestMethod.GET)
    public MessageResponse clearCache() {
        return personService.clearAllCache();
    }
}
