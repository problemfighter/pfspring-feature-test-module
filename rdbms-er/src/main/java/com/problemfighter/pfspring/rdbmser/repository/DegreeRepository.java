package com.problemfighter.pfspring.rdbmser.repository;

import com.problemfighter.pfspring.rdbmser.model.entity.Degree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {

    @Query("SELECT d FROM Degree d")
    Page<Degree> list(Pageable pageable);

}
