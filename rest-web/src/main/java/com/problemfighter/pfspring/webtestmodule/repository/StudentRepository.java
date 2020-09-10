package com.problemfighter.pfspring.webtestmodule.repository;

import com.problemfighter.pfspring.webtestmodule.model.entity.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
}
