package com.problemfighter.pfspring.rdbmser.controller;


import com.problemfighter.pfspring.rdbmser.model.dto.DegreeDTO;
import com.problemfighter.pfspring.rdbmser.model.entity.Degree;
import com.problemfighter.pfspring.rdbmser.repository.DegreeRepository;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/degree")
public class ApiV1DegreeController implements RequestResponse, RestApiAction<DegreeDTO, DegreeDTO, DegreeDTO> {
    
    @Autowired
    private DegreeRepository degreeRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<DegreeDTO> data) {
        Degree entity = requestProcessor().process(data, Degree.class);
        degreeRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<DegreeDTO> bulkCreate(@RequestBody RequestBulkData<DegreeDTO> data) {
        BulkErrorValidEntities<DegreeDTO, Degree> bulkData = requestProcessor().process(data, Degree.class);
        if (bulkData.isValidEntities()) {
            degreeRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, DegreeDTO.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<DegreeDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return responseProcessor().response(degreeRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), DegreeDTO.class);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<DegreeDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    )  {
        return responseProcessor().response(degreeRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), DegreeDTO.class);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<DegreeDTO>  trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return responseProcessor().response(degreeRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), DegreeDTO.class);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<DegreeDTO> details(@PathVariable(name = "id") Long id) {
        return responseProcessor().response(degreeRepository.findById(id), DegreeDTO.class, "Item not found");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<DegreeDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        Degree entity = dataUtil().validateAndOptionToEntity(degreeRepository.findById(id), "Content not found");
        degreeRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<DegreeDTO> bulkUpdate(@RequestBody RequestBulkData<DegreeDTO> data) {
        Iterable<Degree> entities = degreeRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<DegreeDTO, Degree> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            degreeRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, DegreeDTO.class);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> data) {
        Iterable<Degree> entities = degreeRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsDeleted(entities);
        degreeRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> data) {
        Iterable<Degree> entities = degreeRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        degreeRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        Degree entity = dataUtil().validateAndOptionToEntity(degreeRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        degreeRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> data) {
        Iterable<Degree> entities = degreeRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsUndeleted(entities);
        degreeRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }
}
