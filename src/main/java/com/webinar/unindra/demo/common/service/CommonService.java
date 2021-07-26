package com.webinar.unindra.demo.common.service;

import com.webinar.unindra.demo.common.exception.StudyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommonService<T, Z> {

    Long getNum();

    T save(T wrapper) throws StudyException;

    T getById(Z pk) throws StudyException;

    Boolean delete(Z pk) throws StudyException;

    List<T> getAll() throws StudyException;

    void deleteAll() throws StudyException;

    //pageable
    Page<T> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException;
}
