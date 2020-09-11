package com.problemfighter.pfspring.module.es.repository;

import com.problemfighter.pfspring.module.es.model.entity.EsDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EsDepartmentRepository extends ElasticsearchRepository<EsDepartment, String> {

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Page<EsDepartment> findByName(String name, Pageable pageable);

}
