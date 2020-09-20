package com.problemfighter.pfspring.module.es.person.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.problemfighter.java.oc.reflection.ReflectionProcessor;
import com.problemfighter.pfspring.module.es.person.common.EsIndex;
import com.problemfighter.pfspring.module.es.person.model.entity.EsPerson;
import com.problemfighter.pfspring.module.es.person.repository.EsPersonRepository;
import com.problemfighter.pfspring.restapi.rr.RequestResponse;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EsPersonService implements RequestResponse {

    @Autowired
    private EsPersonRepository esPersonRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    private ReflectionProcessor reflectionProcessor;

    public EsPersonService(){
        reflectionProcessor = new ReflectionProcessor();
    }

    public PageableResponse<EsPerson> list(Integer page, Integer size, String sort, String field) {
        if (field == null) {
            field = "name";
        }
        Sort sorting = Sort.by(field).descending();
        if (sort != null && sort.equals("asc")) {
            sorting = Sort.by(field).ascending();
        }
        return responseProcessor().response(esPersonRepository.findAll(PageRequest.of(page, size, sorting)), EsPerson.class);
    }

    public BulkResponse<EsPerson> bulkCreate(RequestBulkData<EsPerson> data) {
        BulkErrorValidEntities<EsPerson, EsPerson> bulkData = requestProcessor().process(data, EsPerson.class);
        if (bulkData.isValidEntities()) {
            esPersonRepository.saveAll(bulkData.getEntities());
        }
        return responseProcessor().response(bulkData, EsPerson.class);
    }

    private SearchRequest getSearchRequest() {
        return new SearchRequest(EsIndex.person);
    }

    private <D> List<D> parseResponse(SearchResponse searchResponse, Class<D> klass) {
        List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<D> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            D data = objectMapper.convertValue(hit.getSourceAsMap(), klass);
            try {
                Field field = reflectionProcessor.getFieldFromObject(data, "id");
                if (field != null) {
                    field.setAccessible(true);
                    field.set(data, hit.getId());
                }
            } catch (Exception ignore) {
            }
            list.add(data);
        }
        return list;
    }

    public DetailsListResponse<EsPerson> findBySex(String sex) {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("sex", sex);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQueryBuilder);
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<EsPerson> list = parseResponse(searchResponse, EsPerson.class);
            DetailsListResponse<EsPerson> response = new DetailsListResponse<>();
            response.data = list;
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DetailsListResponse<EsPerson> findBySex(List<String> sex) {
        String[] sexArray = new String[sex.size()];
        sexArray = sex.toArray(sexArray);
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("sex", sexArray);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termsQueryBuilder);
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<EsPerson> list = parseResponse(searchResponse, EsPerson.class);
            DetailsListResponse<EsPerson> response = new DetailsListResponse<>();
            response.data = list;
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
