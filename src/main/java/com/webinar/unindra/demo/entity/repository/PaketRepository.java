package com.webinar.unindra.demo.entity.repository;

import com.webinar.unindra.demo.entity.Paket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaketRepository extends CrudRepository<Paket, Long> {

    @Query(value = "select p from Paket p where p.description like %:sSearch% or p.namaPaket like %:sSearch%")
    Page<Paket> getPageable(@Param("sSearch") String sSearch, Pageable pageable);
}
