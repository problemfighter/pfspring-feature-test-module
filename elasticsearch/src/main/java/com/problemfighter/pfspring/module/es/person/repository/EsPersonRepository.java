package com.problemfighter.pfspring.module.es.person.repository;

import com.problemfighter.pfspring.module.es.person.model.entity.EsPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsPersonRepository extends ElasticsearchRepository<EsPerson, String> {


}
