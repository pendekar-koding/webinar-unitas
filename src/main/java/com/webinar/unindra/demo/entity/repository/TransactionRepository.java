package com.webinar.unindra.demo.entity.repository;

import com.webinar.unindra.demo.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t where t.description like %:sSearch%")
    Page<Transaction> getPageable(@Param("sSearch") String sSearch, Pageable pageable);

    @Query(value = "select t from Transaction t where t.deleted = false and (t.modifiedDate between :startDate and :endDate) ")
    Page<Transaction> getPageableByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate, Pageable pageable);
}
