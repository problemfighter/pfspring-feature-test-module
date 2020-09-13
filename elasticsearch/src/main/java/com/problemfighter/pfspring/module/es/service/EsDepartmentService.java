package com.problemfighter.pfspring.module.es.service;

import com.problemfighter.pfspring.module.es.model.entity.EsDepartment;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class EsDepartmentService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;



    public Object getAll(String text)  {

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery(text)
                                .lenient(true)
                                .field("name")
                                .field("code")
                ).should(QueryBuilders.queryStringQuery("*" + text + "*")
                        .lenient(true)
                        .field("name")
                        .field("code"));

//        SearchQuery  build = new NativeSearchQueryBuilder()
//                .withQuery(query)
//                .build();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("----------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        NativeSearchQuery


//        return restHighLevelClient.(build, EsDepartment.class);
//        return elasticsearchOperations.search(query, EsDepartment.class);
        return null;
    }

    private void aggregationExample(){
        SearchRequest searchRequest = new SearchRequest("zipkin*");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(AggregationBuilders
                .terms("group_by_traceid")
                .field("traceId")
                .order(BucketOrder.aggregation("start_time", false))
                .subAggregation(AggregationBuilders
                        .min("start_time")
                        .field("timestamp"))
        );
        searchRequest.source(searchSourceBuilder);
    }


    private static void testAggregation(RestHighLevelClient client, String myQueryString) throws IOException
    {
// test of aggregation

// building the search (query  + aggregations)
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

// setting the query which is used for the aggregation
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(myQueryString));

// build the aggregation
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_msisdn")
                .field("msisdn.keyword");
        aggregation.subAggregation(AggregationBuilders.count("count"));
        searchSourceBuilder.aggregation(aggregation);

// create the request
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);

// fire the request and get the response
//        SearchResponse searchResponse = new SearchResponse();
//        searchResponse = client.search(searchRequest);


// get aggregations:
//        Aggregations aggregations = searchResponse.getAggregations();
//        Terms byMsisdn = aggregations.get("by_msisdn");

    }

    public void groupBy(){

        try{
            AggregationBuilder aggregation =
                    AggregationBuilders
                            .filters("agg",
                                    new FiltersAggregator.KeyedFilter("men", QueryBuilders.termQuery("code", "CSE")),
                                    new FiltersAggregator.KeyedFilter("women", QueryBuilders.termQuery("code", "CSIT")));

            SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.source(builder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


            Filters agg = searchResponse.getAggregations().get("agg");

            for (Filters.Bucket entry : agg.getBuckets()) {
                String key = entry.getKeyAsString();            // bucket key
                long docCount = entry.getDocCount();            // Doc count
                System.out.printf("key [{}], doc_count [{}]", key, docCount);
            }
        }catch (Exception ignore){

        }

    }

    public void countCse(){
        try{
            groupBy();
            ValueCountAggregationBuilder count = AggregationBuilders.count("count").field("code");
//            SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(count);

//            SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(AggregationBuilders
//                    .global("agg")
//                    .subAggregation(AggregationBuilders.terms("genders").field("code")));

            SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(AggregationBuilders
                    .filter("agg", QueryBuilders.termQuery("code", "CSE")));


            SearchRequest searchRequest = new SearchRequest();
            searchRequest.source(builder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println("xyz");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ex1() throws IOException {
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("top_tags")
                .field("tags");
//                .order();
        SearchSourceBuilder builder = new SearchSourceBuilder().aggregation(aggregation);

        SearchRequest searchRequest =
                new SearchRequest().indices("blog").types("article").source(builder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Map<String, Aggregation> results = response.getAggregations().asMap();
        StringTerms topTags = (StringTerms) results.get("top_tags");

        List<String> keys = topTags.getBuckets()
                .stream()
                .map(b -> b.getKeyAsString())
                .collect(toList());
    }

}
