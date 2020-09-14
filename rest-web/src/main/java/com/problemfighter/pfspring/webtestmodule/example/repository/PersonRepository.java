package com.problemfighter.pfspring.webtestmodule.example.repository;

import com.problemfighter.pfspring.webtestmodule.example.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT d FROM Person d WHERE d.isDeleted =:isDeleted")
    Page<Person> list(Pageable pageable, Boolean isDeleted);
}
