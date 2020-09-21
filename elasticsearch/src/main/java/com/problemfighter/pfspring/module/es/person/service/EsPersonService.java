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
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

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

    private SearchRequest getSearchRequest(SearchSourceBuilder sourceBuilder) {
        return new SearchRequest(EsIndex.person).source(sourceBuilder);
    }

    private SearchSourceBuilder getSourceBuilder(QueryBuilder query) {
        return new SearchSourceBuilder().query(query);
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
        SearchSourceBuilder searchSourceBuilder = getSourceBuilder(matchQueryBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
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

    public DetailsListResponse<EsPerson> findByAgeRange(Double start, Double end) {
        QueryBuilder ageQuery = QueryBuilders.rangeQuery("age").from(start).to(end);
        SearchSourceBuilder searchSourceBuilder = getSourceBuilder(ageQuery);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
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

    public DetailsListResponse<EsPerson> findByNotEqualSex(List<String> sex) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        String[] sexArray = new String[sex.size()];
        sexArray = sex.toArray(sexArray);
        builder.mustNot(QueryBuilders.termsQuery("sex", sexArray));
        SearchSourceBuilder searchSourceBuilder = getSourceBuilder(builder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
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

    public Double averageIncome(){
        AvgAggregationBuilder aggregationBuilder = AggregationBuilders.avg("agg").field("income");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Avg out = searchResponse.getAggregations().get("agg");
            return out.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double maxIncome(){
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("agg").field("income");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Max out = searchResponse.getAggregations().get("agg");
            return out.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double maxIncomeByOccupation(String occupation){
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("agg").field("income");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregationBuilder);
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("occupation", occupation);
        searchSourceBuilder.query(termsQueryBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Max out = searchResponse.getAggregations().get("agg");
            return out.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double sumOfTotalIncome() {
        SumAggregationBuilder sumAggregationBuilder = AggregationBuilders.sum("sum_of_all_income").field("income");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(sumAggregationBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Sum sum = searchResponse.getAggregations().get("sum_of_all_income");
            return sum.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void distinctOccupation(){

    }

    public void statsAggregation(){

    }

    public Map<String, Object> groupByOccupation() {
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by").field("occupation");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Map<String, Object> res = new HashMap<>();
            Terms genders = searchResponse.getAggregations().get("group_by");
            for (Terms.Bucket entry : genders.getBuckets()) {
                res.put(entry.getKey().toString(), entry.getDocCount());
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DetailsListResponse<EsPerson> findBySex(List<String> sex) {
        String[] sexArray = new String[sex.size()];
        sexArray = sex.toArray(sexArray);
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("sex", sexArray);
        SearchSourceBuilder searchSourceBuilder = getSourceBuilder(termsQueryBuilder);
        SearchRequest searchRequest = getSearchRequest(searchSourceBuilder);
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
