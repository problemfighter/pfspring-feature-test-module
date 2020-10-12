package com.problemfighter.pfspring.module.redis.repository;

import com.problemfighter.pfspring.module.redis.model.entity.RedisPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RedisPersonRepository extends CrudRepository<RedisPerson, Long> {

    @Query("SELECT d FROM RedisPerson d WHERE d.isDeleted =:isDeleted")
    Page<RedisPerson> list(Pageable pageable, Boolean isDeleted);

    @Query("SELECT d FROM RedisPerson d WHERE d.email =:email")
    RedisPerson findByEmail(String email);

    @Query("SELECT d FROM RedisPerson d WHERE d.email =:email AND d.id =:id")
    RedisPerson findByEmailAndId(String email, Long id);
}
