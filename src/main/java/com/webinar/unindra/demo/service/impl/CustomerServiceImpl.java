package com.webinar.unindra.demo.service.impl;

import com.webinar.unindra.demo.common.exception.ErrorCode;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.message.DataTableObject;
import com.webinar.unindra.demo.entity.Customer;
import com.webinar.unindra.demo.entity.repository.CustomerRepository;
import com.webinar.unindra.demo.service.CustomerService;
import com.webinar.unindra.demo.wrapper.CustomerWrapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private CustomerWrapper toWrapper(Customer entity){
        CustomerWrapper wrapper = new CustomerWrapper();
        wrapper.setId(entity.getId());
        wrapper.setDescription(entity.getDescription());
        wrapper.setVersion(entity.getVersion());
        wrapper.setDeleted(entity.getDeleted());

        wrapper.setNama(entity.getNama());
        wrapper.setEmail(entity.getEmail());
        wrapper.setMobilePhone(entity.getMobilePhone());

        return wrapper;
    }

    private Customer toEntity(CustomerWrapper wrapper){
        Customer entity = new Customer();
        if (wrapper.getId() != null){
            Optional<Customer> optional = customerRepository.findById(wrapper.getId());
            if (optional.isPresent()){
                entity = optional.get();
            }
        }
        entity.setDescription(wrapper.getDescription());
        entity.setDeleted(wrapper.getDeleted());
        entity.setVersion(wrapper.getVersion());

        entity.setNama(wrapper.getNama());
        entity.setEmail(wrapper.getEmail());
        entity.setMobilePhone(wrapper.getMobilePhone());

        return entity;
    }

    private List<CustomerWrapper> toWrapperList(List<Customer> modelList){
        List<CustomerWrapper> rList = new ArrayList<>();
        if (modelList != null && !modelList.isEmpty()){
            for (Customer entity : modelList){
                rList.add(toWrapper(entity));
            }
        }
        return rList;
    }


    @Override
    public Long getNum() {
        return customerRepository.count();
    }

    @Override
    public CustomerWrapper save(CustomerWrapper wrapper) throws StudyException {
        wrapper.setVersion(1);
        wrapper.setDeleted(false);
        return toWrapper(customerRepository.save(toEntity(wrapper)));
    }

    @Override
    public CustomerWrapper getById(Long pk) throws StudyException {
        Optional<Customer> optional = customerRepository.findById(pk);
        return optional.map(this::toWrapper).orElse(null);
    }

    @Override
    public Boolean delete(Long pk) throws StudyException {
        try {
            customerRepository.deleteById(pk);
            return true;
        } catch (Exception e){
            throw new StudyException(e, ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public List<CustomerWrapper> getAll() throws StudyException {
        return toWrapperList((List<Customer>) customerRepository.findAll());
    }

    @Override
    public void deleteAll() throws StudyException {
        //Not Implement yet
    }

    @Override
    public Page<CustomerWrapper> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        //hsql query contains deleted = false, we need to manipulate the result in case the database table empty
        if (customerRepository.count() == 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        } else {
            Page<Customer> pageableModel = customerRepository.getPageable(sSearch, pageable);
            List<CustomerWrapper> wrapperList = toWrapperList(pageableModel.getContent());
            return new PageImpl<>(wrapperList, pageable, pageableModel.getTotalElements());
        }
    }
}
