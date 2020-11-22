package com.problemfighter.pfspring.rdbmser.repository;

import com.problemfighter.pfspring.rdbmser.model.entity.Address;
import com.problemfighter.pfspring.rdbmser.model.entity.Degree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a")
    Page<Address> list(Pageable pageable);

}
