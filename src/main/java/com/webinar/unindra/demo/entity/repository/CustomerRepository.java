package com.webinar.unindra.demo.entity.repository;

import com.webinar.unindra.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "select c from Customer c where c.description like %:sSearch% or c.nama like %:sSearch% or c.mobilePhone like %:sSearch%")
    Page<Customer> getPageable(@Param("sSearch") String sSearch, Pageable pageable);
}
