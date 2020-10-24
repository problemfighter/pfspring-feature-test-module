package com.problemfighter.pfspring.hl2c.repository;

import com.problemfighter.pfspring.hl2c.model.entity.Hl2cPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Hl2cPersonRepository extends CrudRepository<Hl2cPerson, Long> {

    @Query("SELECT d FROM RedisPerson d WHERE d.isDeleted =:isDeleted")
    Page<Hl2cPerson> list(Pageable pageable, Boolean isDeleted);

    @Query("SELECT d FROM Hl2cPerson d WHERE d.email =:email")
    Hl2cPerson findByEmail(String email);

    @Query("SELECT d FROM Hl2cPerson d WHERE d.email =:email AND d.id =:id")
    Hl2cPerson findByEmailAndId(String email, Long id);
}
