package com.problemfighter.pfspring.rdbmser.repository;

import com.problemfighter.pfspring.rdbmser.model.entity.Address;
import com.problemfighter.pfspring.rdbmser.model.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p")
    Page<Person> list(Pageable pageable);

}
