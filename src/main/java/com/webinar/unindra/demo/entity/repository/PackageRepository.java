package com.webinar.unindra.demo.entity.repository;

import com.webinar.unindra.demo.entity.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends CrudRepository<Package, Long> {

    @Query(value = "select p from Package p where p.description like %:sSearch% or p.name like %:sSearch%")
    Page<Package> getPageable(@Param("sSearch") String sSearch, Pageable pageable);
}
