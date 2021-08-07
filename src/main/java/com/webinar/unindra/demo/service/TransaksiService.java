package com.webinar.unindra.demo.service;

import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.service.CommonService;
import com.webinar.unindra.demo.wrapper.TransaksiWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.text.ParseException;

public interface TransaksiService extends CommonService<TransaksiWrapper, Long> {
    Page<TransaksiWrapper> getPageableByDate(String startDate, String endDate, int startPage, int pageSize, Sort sort) throws StudyException, ParseException;
}
