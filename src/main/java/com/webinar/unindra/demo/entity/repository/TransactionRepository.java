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

    @Query(value = "select t from Transaction t " +
            "join Customer c on c.id = t.customer.id " +
            "join Package p on p.id = t.aPackage.id" +
            " where (t.modifiedDate between :startDate and :endDate)")
    Page<Transaction> getPageableByDate(@Param("startDate")Date startDate,
                                        @Param("endDate")Date endDate,
                                        Pageable pageable);
}
