package com.problemfighter.pfspring.rdbmser.controller;


import com.problemfighter.pfspring.rdbmser.model.dto.AddressDTO;
import com.problemfighter.pfspring.rdbmser.model.entity.Address;
import com.problemfighter.pfspring.rdbmser.repository.AddressRepository;
import com.problemfighter.pfspring.restapi.inter.RestApiAction;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
public class ApiV1AddressController implements RequestResponse, RestApiAction<AddressDTO, AddressDTO, AddressDTO> {
    
    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResponse create(@RequestBody RequestData<AddressDTO> data) {
        Address entity = requestProcessor().process(data, Address.class);
        addressRepository.save(entity);
        return responseProcessor().response("Created");
    }

    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public BulkResponse<AddressDTO> bulkCreate(@RequestBody RequestBulkData<AddressDTO> data) {
        BulkErrorValidEntities<AddressDTO, Address> bulkData = requestProcessor().process(data, Address.class);
        if (bulkData.isValidEntities()) {
            addressRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, AddressDTO.class);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageableResponse<AddressDTO> list(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return responseProcessor().response(addressRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), AddressDTO.class);
    }

    @RequestMapping(value = "/detail-list", method = RequestMethod.GET)
    public PageableResponse<AddressDTO> detailList(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    )  {
        return responseProcessor().response(addressRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), AddressDTO.class);
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public PageableResponse<AddressDTO>  trash(
            @RequestParam(value = "page", defaultValue = "") Integer page,
            @RequestParam(value = "size", defaultValue = "") Integer size,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "field", defaultValue = "") String field,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        return responseProcessor().response(addressRepository.list(requestProcessor().paginationNSort(page, size, sort, field)), AddressDTO.class);
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public DetailsResponse<AddressDTO> details(@PathVariable(name = "id") Long id) {
        return responseProcessor().response(addressRepository.findById(id), AddressDTO.class, "Item not found");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public MessageResponse update(@RequestBody RequestData<AddressDTO> data) {
        Long id = requestProcessor().validateId(data, "Id not found");
        Address entity = dataUtil().validateAndOptionToEntity(addressRepository.findById(id), "Content not found");
        addressRepository.save(entity);
        return responseProcessor().response("Updated");
    }

    @RequestMapping(value = "/bulk-update", method = RequestMethod.PATCH)
    public BulkResponse<AddressDTO> bulkUpdate(@RequestBody RequestBulkData<AddressDTO> data) {
        Iterable<Address> entities = addressRepository.findAllById(dataUtil().getAllId(data));
        BulkErrorValidEntities<AddressDTO, Address> bulkData = dataUtil().merge(entities, data);
        if (bulkData.isValidEntities()) {
            addressRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, AddressDTO.class);
    }

    @RequestMapping(value = "/bulk-delete", method = RequestMethod.DELETE)
    public MessageResponse bulkDelete(@RequestBody RequestBulkData<Long> data) {
        Iterable<Address> entities = addressRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsDeleted(entities);
        addressRepository.saveAll(entities);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/hard-delete", method = RequestMethod.DELETE)
    public MessageResponse hardDelete(@RequestBody RequestBulkData<Long> data) {
        Iterable<Address> entities = addressRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        addressRepository.deleteAll(entities);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResponse delete(@PathVariable(name = "id") Long id) {
        id = requestProcessor().validateId(id, "Id not found");
        Address entity = dataUtil().validateAndOptionToEntity(addressRepository.findById(id), "Content not found");
        dataUtil().markAsDeleted(entity);
        addressRepository.save(entity);
        return responseProcessor().response("Deleted");
    }

    @RequestMapping(value = "/bulk-restore", method = RequestMethod.PATCH)
    public MessageResponse bulkRestore(@RequestBody RequestBulkData<Long> data) {
        Iterable<Address> entities = addressRepository.findAllById(data.getData());
        if (dataUtil().isEmpty(entities)) {
            return responseProcessor().error("Content not found");
        }
        dataUtil().markAsUndeleted(entities);
        addressRepository.saveAll(entities);
        return responseProcessor().response("Restored");
    }
}
