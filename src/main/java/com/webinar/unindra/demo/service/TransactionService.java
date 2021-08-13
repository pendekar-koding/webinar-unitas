package com.webinar.unindra.demo.service;

import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.wrapper.TransactionWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.util.List;

public interface TransactionService {

    TransactionWrapper save(TransactionWrapper wrapper) throws StudyException;

    TransactionWrapper getById(Long pk) throws StudyException;

    Boolean delete(Long pk) throws StudyException;

    List<TransactionWrapper> getAll() throws StudyException;

    Page<TransactionWrapper> getPageableByDate(String startDate, String endDate, int startPage, int pageSize, Sort sort) throws StudyException, ParseException;
}
