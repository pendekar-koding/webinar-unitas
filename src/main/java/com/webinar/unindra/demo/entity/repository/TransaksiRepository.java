package com.webinar.unindra.demo.entity.repository;

import com.webinar.unindra.demo.entity.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransaksiRepository extends CrudRepository<Transaksi, Long> {
    @Query(value = "select t from Transaksi t where t.description like %:sSearch%")
    Page<Transaksi> getPageable(@Param("sSearch") String sSearch, Pageable pageable);

    @Query(value = "select t from Transaksi t where t.deleted = false and (t.modifiedDate between :startDate and :endDate) ")
    Page<Transaksi> getPageableByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate, Pageable pageable);
}
