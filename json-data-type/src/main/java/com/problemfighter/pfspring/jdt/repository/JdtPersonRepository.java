package com.problemfighter.pfspring.jdt.repository;

import com.problemfighter.pfspring.jdt.model.entity.JdtPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JdtPersonRepository extends CrudRepository<JdtPerson, Long> {

    @Query("SELECT d FROM JdtPerson d WHERE d.isDeleted =:isDeleted")
    Page<JdtPerson> list(Pageable pageable, Boolean isDeleted);

    @Query("SELECT d FROM JdtPerson d WHERE d.email =:email")
    JdtPerson findByEmail(String email);

    @Query("SELECT d FROM JdtPerson d WHERE d.email =:email AND d.id =:id")
    JdtPerson findByEmailAndId(String email, Long id);
}
