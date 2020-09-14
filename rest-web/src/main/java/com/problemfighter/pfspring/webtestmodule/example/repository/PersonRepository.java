package com.problemfighter.pfspring.webtestmodule.example.repository;

import com.problemfighter.pfspring.webtestmodule.example.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT d FROM Person d WHERE d.isDeleted =:isDeleted")
    Page<Person> list(Pageable pageable, Boolean isDeleted);

    @Query("SELECT d FROM Person d WHERE d.email =:email")
    Person findByEmail(String email);

    @Query("SELECT d FROM Person d WHERE d.email =:email AND d.id =:id")
    Person findByEmailAndId(String email, Long id);
}
