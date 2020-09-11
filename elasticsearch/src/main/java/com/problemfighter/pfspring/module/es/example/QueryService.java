package com.problemfighter.pfspring.module.es.example;

import org.apache.lucene.index.Terms;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.ml.job.results.Bucket;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class QueryService {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<EsBlog> elasticSearchTest() {

        //1. Create Query Builder (that is, set query conditions). Here we create a combination query (also known as multi-condition query). More query methods will be introduced later.
        /*Combination Query Builder
         * must(QueryBuilders)   :AND
         * mustNot(QueryBuilders):NOT
         * should:               :OR
         */
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //Under builder, must, should and mustNot are equivalent to and, or and not in sql

        //Setting up a vague search, there are two words in the brief comment of the blog: learning
        builder.must(QueryBuilders.fuzzyQuery("sumary", "Study"));

        //Set the title of the blog to be queried to contain keywords
        builder.must(new QueryStringQueryBuilder("man").field("springdemo"));

        //The ranking of blog comments is decreasing in turn
        FieldSortBuilder sort = SortBuilders.fieldSort("commentSize").order(SortOrder.DESC);

        //Set Paging (10 items are displayed on the first page)
        //Note that the start is from 0, a bit like the query for method limit in sql
        PageRequest pagination = PageRequest.of(0, 10);

        //2. Building queries
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //Set search criteria to build
        nativeSearchQueryBuilder.withQuery(builder);
        //Setting paging to build
        nativeSearchQueryBuilder.withPageable(pagination);
        //Set the sort to build
        nativeSearchQueryBuilder.withSort(sort);
        //Production of Native SearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        //3. Execution Method 1
        Page<EsBlog> page = esBlogRepository.search(query);
        //Execution Method 2: Note that there is another way to execute it here: using elastic search Template
        //Annotations need to be added when executing Method 2


//        List<EsBlog> blogList = elasticsearchTemplate.queryForList(query, EsBlog.class);

        //4. Get the total number of entries (for front-end paging)
        int total = (int) page.getTotalElements();

        //5. Get the queried data content (returned to the front end)
        List<EsBlog> content = page.getContent();

        return content;
    }


    public void singleMatchingTermQuery() {
        //Non-separable query parameter 1: field name, parameter 2: field query value, because non-separable, so Chinese characters can only query a word, English is a word.
        QueryBuilder queryBuilder = QueryBuilders.termQuery("fieldName", "fieldlValue");

        //Word segmentation query, using default word segmentation device
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("fieldName", "fieldlValue");
    }

    public void multipleMatches() {
        //Non-segmented query, parameter 1: field name, parameter 2: multi-field query value, because non-segmented, so Chinese characters can only query a word, English is a word.
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("fieldName", "fieldlValue1", "fieldlValue2...");

        //Word segmentation query, using default word segmentation device
        QueryBuilder queryBuilder1 = QueryBuilders.multiMatchQuery("fieldlValue", "fieldName1", "fieldName2", "fieldName3");

        //Matching all files means no query conditions are set
        QueryBuilder queryBuilder2 = QueryBuilders.matchAllQuery();
    }


    public void fuzzyQuery(){
        //Five common methods of fuzzy query are as follows
        //1. Common string queries
        QueryBuilders.queryStringQuery("fieldValue").field("fieldName");//Left-right ambiguity

        //2. Queries commonly used to recommend similar content
        QueryBuilders.moreLikeThisQuery(new String[] {"fieldName"});  //.addLikeText("pipeidhua");
        //If filedName is not specified, it defaults to all, commonly used in recommendation of similar content.

        //3. Prefix query: If the field has no participle, it matches the whole field prefix
        QueryBuilders.prefixQuery("fieldName","fieldValue");


        //4.fuzzy query: A participle-based fuzzy query, which is queried by adding fuzzy attributes. If a document can match hotelName with a letter before or after tel, the meaning of fuzzy query is to add or decrease n words before and after the term.
        QueryBuilders.fuzzyQuery("hotelName", "tel").fuzziness(Fuzziness.ONE);

        //5.wildcard query: wildcard query, supporting * arbitrary strings;? Any character
        QueryBuilders.wildcardQuery("fieldName","ctr*");//The first is field name, and the second is a string with matching characters.
        QueryBuilders.wildcardQuery("fieldName","c?r?");
    }


    public void rangeQuery(){
        //Closed Interval Query
        QueryBuilder queryBuilder0 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");

        //Open Interval Query
        QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2").includeUpper(false).includeLower(false);//The default is true, which is to include

        //greater than
        QueryBuilder queryBuilder2 = QueryBuilders.rangeQuery("fieldName").gt("fieldValue");

        //Greater than or equal to
        QueryBuilder queryBuilder3 = QueryBuilders.rangeQuery("fieldName").gte("fieldValue");

        //less than
        QueryBuilder queryBuilder4 = QueryBuilders.rangeQuery("fieldName").lt("fieldValue");

        //Less than or equal to
        QueryBuilder queryBuilder5 = QueryBuilders.rangeQuery("fieldName").lte("fieldValue");
    }

    public void combinationQuery(){
        QueryBuilders.boolQuery();
        QueryBuilders.boolQuery().must();//Documents must match conditions exactly, equivalent to and
        QueryBuilders.boolQuery().mustNot();//Documents must not match conditions, equivalent to not
        QueryBuilders.boolQuery().should();//If at least one condition is met, the document will meet should, equivalent to or
    }


    public void aggregatedQuery(){
        //Goal: Search for the most blogged users (one blog corresponds to one user) and achieve the desired results by searching for the frequency of user names in the blog
        //First create a new collection for storing data
        List<String> ueserNameList=new ArrayList<>();
        //1. Create query conditions, namely QueryBuild
        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();//Setting all queries is equivalent to not setting query conditions
        //2. Building queries
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //2.0 Setting QueryBuilder
        nativeSearchQueryBuilder.withQuery(matchAllQuery);
        //2.1 Set the search type. The default value is QUERY_THEN_FETCH. Refer to https://blog.csdn.net/wulex/article/details/71081042.
        nativeSearchQueryBuilder.withSearchType(SearchType.QUERY_THEN_FETCH);//Specify the type of index, query only the matching documents from each fragment, then reorder and rank, and get the first size documents.
        //2.2 Specify index libraries and document types
     //   nativeSearchQueryBuilder.withIndices("myBlog").withTypes("blog");//Specify the name and type of the index library to query, which is actually the indedName and type set in our document @Document
        //2.3 Here comes the point!!! Specify aggregation functions. In this case, take a field grouping aggregation as an example (you can set it according to your own aggregation query requirements)
        //The aggregation function explains: Calculate the frequency of occurrence of the field (assumed to be username) in all documents and rank it in descending order (usually used for a field's thermal ranking)
      //  TermsBuilder termsAggregation = AggregationBuilders.terms("Name for aggregate queries").field("username").order(Terms.Order.count(false));
      //  nativeSearchQueryBuilder.addAggregation(termsAggregation);
        //2.4 Building Query Objects
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        //3. Executing queries
        //3.1 Method 1, queries are executed through reporitory to obtain a Page-wrapped result set
        Page<EsBlog> search = esBlogRepository.search(nativeSearchQuery);
        List<EsBlog> content = search.getContent();
        for (EsBlog esBlog : content) {
           // ueserNameList.add(esBlog.getUsername());
        }
        //After I get the corresponding document, I can get the author of the document, and then I can find the most popular users.
        //3.2 Method 2, query by the elastic search Template. queryForList method of the elastic Search template
      //  List<EsBlog> queryForList = elasticsearchTemplate.queryForList(nativeSearchQuery, EsBlog.class);
        //3.3 Method 3. By querying the elastic search Template. query () method of the elastic Search template, the aggregation (commonly used) can be obtained.
//        Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, new ResultsExtractor<Aggregations>() {
//            @Override
//            public Aggregations extract(SearchResponse response) {
//                return response.getAggregations();
//            }
//        });
        //Converting to map sets
//        Map<String, Aggregation> aggregationMap = aggregations.asMap();
        //Get the aggregation subclass of the corresponding aggregation function. The aggregation subclass is also a map set. The value inside is the bucket Bucket. We want to get the Bucket.
//        StringTerms stringTerms = (StringTerms) aggregationMap.get("Name for aggregate queries");
        //Get all the buckets
//        List<Bucket> buckets = stringTerms.getBuckets();
        //Converting a collection into an iterator traversal bucket, of course, if you don't delete the elements in buckets, just go ahead and traverse it.
        Iterator<Bucket> iterator = null; // buckets.iterator();

        while(iterator.hasNext()) {
            //The bucket bucket is also a map object, so we can just take its key value.
          //  String username = iterator.next().getKeyAsString();//Or bucket.getKey().toString();
            //According to username, the corresponding document can be queried in the result, and the set of stored data can be added.
            // ueserNameList.add(username);
        }
        //Finally, search the corresponding result set according to ueserNameList
//        List<User> listUsersByUsernames = userService.listUsersByUsernames(ueserNameList);
    }

}
