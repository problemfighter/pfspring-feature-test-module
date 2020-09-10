package com.problemfighter.pfspring.webtestmodule.repository;

import com.problemfighter.pfspring.webtestmodule.model.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.code = :code")
    Department findDepartmentByCode(String code);

    @Query("SELECT d FROM Department d WHERE d.code = :code AND d.id = :id")
    Department findDepartmentByCodeAndId(String code, Long id);

    @Query("SELECT d FROM Department d WHERE d.isDeleted =:isDeleted")
    Page<Department> list(Pageable pageable, Boolean isDeleted);
}
