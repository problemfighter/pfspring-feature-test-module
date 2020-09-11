package com.problemfighter.pfspring.module.es;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"com.problemfighter.pfspring.module.es.*"})
@EnableElasticsearchRepositories("com.problemfighter.pfspring.module.es.repository")
public class ElasticsearchScanner {
}
