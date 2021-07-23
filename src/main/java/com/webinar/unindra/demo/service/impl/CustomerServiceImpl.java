package com.webinar.unindra.demo.service.impl;

import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.entity.repository.CustomerRepository;
import com.webinar.unindra.demo.service.CustomerService;
import com.webinar.unindra.demo.wrapper.CustomerWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }




    @Override
    public Long getNum() {
        return null;
    }

    @Override
    public CustomerWrapper save(CustomerWrapper entity) throws StudyException {
        return null;
    }

    @Override
    public CustomerWrapper getById(Long pk) throws StudyException {
        return null;
    }

    @Override
    public Boolean delete(Long pk) throws StudyException {
        return null;
    }

    @Override
    public List<CustomerWrapper> getAll() throws StudyException {
        return null;
    }

    @Override
    public void deleteAll() throws StudyException {

    }

    @Override
    public Page<CustomerWrapper> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException {
        return null;
    }
}
