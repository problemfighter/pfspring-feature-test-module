package com.problemfighter.pfspring.module.es.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchQueryService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void findByName() {

    }

}
