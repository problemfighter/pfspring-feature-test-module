package com.problemfighter.pfspring.module.es;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"com.problemfighter.pfspring.module.es.*"})
@EnableElasticsearchRepositories(value = {"com.problemfighter.pfspring.module.es.repository", "com.problemfighter.pfspring.module.es.person.repository"})
public class ElasticsearchScanner {
}
