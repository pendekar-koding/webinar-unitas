package com.webinar.unindra.demo.service.impl;

import com.webinar.unindra.demo.common.exception.ErrorCode;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.message.DataTableObject;
import com.webinar.unindra.demo.entity.Customer;
import com.webinar.unindra.demo.entity.Package;
import com.webinar.unindra.demo.entity.Transaction;
import com.webinar.unindra.demo.entity.repository.CustomerRepository;
import com.webinar.unindra.demo.entity.repository.PackageRepository;
import com.webinar.unindra.demo.entity.repository.TransactionRepository;
import com.webinar.unindra.demo.service.TransactionService;
import com.webinar.unindra.demo.wrapper.TransactionWrapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final PackageRepository packageRepository;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransactionServiceImpl(TransactionRepository transactionRepository, CustomerRepository customerRepository, PackageRepository packageRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.packageRepository = packageRepository;
    }

    private TransactionWrapper toWrapper(Transaction entity){
        TransactionWrapper wrapper = new TransactionWrapper();
        wrapper.setId(entity.getId());
        wrapper.setDescription(entity.getDescription());
        wrapper.setDeleted(entity.getDeleted());
        wrapper.setVersion(entity.getVersion());
        wrapper.setUpdatedDate(entity.getModifiedDate());

        wrapper.setCodeTransaction("TRS-"+entity.getId());
        wrapper.setTotal(entity.getTotal());
        wrapper.setTotalPrice(entity.getTotalPrice());

        wrapper.setIdCustomer(entity.getCustomer().getId());
        wrapper.setNamaCustomer(entity.getCustomer().getName());

        wrapper.setIdPaket(entity.getaPackage().getId());
        wrapper.setNamaPaket(entity.getaPackage().getName());

        return wrapper;
    }

    private Transaction toEntity(TransactionWrapper wrapper){
        Transaction entity = new Transaction();
        if (wrapper.getId() != null){
            Optional<Transaction> optional = transactionRepository.findById(wrapper.getId());
            if (optional.isPresent()){
                entity = optional.get();
            }
        }
        entity.setDescription(wrapper.getDescription());
        entity.setDeleted(wrapper.getDeleted());
        entity.setVersion(wrapper.getVersion());
        entity.setModifiedDate(wrapper.getUpdatedDate());

        entity.setTotal(wrapper.getTotal());
        entity.setTotalPrice(wrapper.getTotalPrice());

        if (wrapper.getIdCustomer() != null){
            Optional<Customer> optional = customerRepository.findById(wrapper.getIdCustomer());
            if (optional.isPresent()){
                entity.setCustomer(optional.get());
            }
        }
        if (wrapper.getIdPaket() != null){
            Optional<Package> optional = packageRepository.findById(wrapper.getIdPaket());
            if (optional.isPresent()){
                entity.setaPackage(optional.get());
            }
        }
        return entity;
    }

    private List<TransactionWrapper> toWrapperList(List<Transaction> modelList){
        List<TransactionWrapper> rList = new ArrayList<>();
        if (modelList != null && !modelList.isEmpty()){
            for (Transaction entity : modelList){
                rList.add(toWrapper(entity));
            }
        }
        return rList;
    }

    private Page<Transaction> getFilterByDate(String startDate, String endDate, Pageable pageable) throws ParseException {
        Date startDay = simpleDateFormat.parse(startDate);
        Date endDay = simpleDateFormat.parse(endDate);
        return transactionRepository.getPageableByDate(startDay, endDay, pageable);
    }

    @Override
    public Page<TransactionWrapper> getPageableByDate(String startDate, String endDate, int startPage, int pageSize, Sort sort) throws StudyException, ParseException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Transaction> transaksiPage = getFilterByDate(startDate, endDate, pageable);
        List<TransactionWrapper> transactionWrappers =toWrapperList(transaksiPage.getContent());
        return new PageImpl<>(transactionWrappers, pageable, transaksiPage.getTotalElements());
    }

    @Override
    public Long getNum() {
        return transactionRepository.count();
    }

    @Override
    public TransactionWrapper save(TransactionWrapper wrapper) throws StudyException {
        wrapper.setVersion(1);
        wrapper.setDeleted(false);
        return toWrapper(transactionRepository.save(toEntity(wrapper)));
    }

    @Override
    public TransactionWrapper getById(Long pk) throws StudyException {
        Optional<Transaction> optional = transactionRepository.findById(pk);
        return optional.map(this::toWrapper).orElse(null);
    }

    @Override
    public Boolean delete(Long pk) throws StudyException {
        try {
            transactionRepository.deleteById(pk);
            return true;
        } catch (Exception e){
            throw new StudyException(e, ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public List<TransactionWrapper> getAll() throws StudyException {
        return toWrapperList((List<Transaction>) transactionRepository.findAll());
    }

    @Override
    public void deleteAll() throws StudyException {
        //Not Implement yet

    }

    @Override
    public Page<TransactionWrapper> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        if (transactionRepository.count() == 0){
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        } else {
            Page<Transaction> transaksiPage = transactionRepository.getPageable(sSearch, pageable);
            List<TransactionWrapper> wrappers = toWrapperList(transaksiPage.getContent());
            return new PageImpl<>(wrappers, pageable, transaksiPage.getTotalElements());
        }
    }
}
