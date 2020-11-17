package com.problemfighter.pfspring.webtestmodule.example.repository;

import com.problemfighter.pfspring.webtestmodule.example.model.entity.ExPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<ExPerson, Long> {

    @Query("SELECT d FROM ExPerson d WHERE d.isDeleted =:isDeleted")
    Page<ExPerson> list(Pageable pageable, Boolean isDeleted);

    @Query("SELECT d FROM ExPerson d WHERE d.email =:email")
    ExPerson findByEmail(String email);

    @Query("SELECT d FROM ExPerson d WHERE d.email =:email AND d.id =:id")
    ExPerson findByEmailAndId(String email, Long id);
}
