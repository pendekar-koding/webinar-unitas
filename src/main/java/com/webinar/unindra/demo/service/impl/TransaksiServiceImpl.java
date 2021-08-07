package com.webinar.unindra.demo.service.impl;

import com.webinar.unindra.demo.common.exception.ErrorCode;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.message.DataTableObject;
import com.webinar.unindra.demo.entity.Customer;
import com.webinar.unindra.demo.entity.Paket;
import com.webinar.unindra.demo.entity.Transaksi;
import com.webinar.unindra.demo.entity.repository.CustomerRepository;
import com.webinar.unindra.demo.entity.repository.PaketRepository;
import com.webinar.unindra.demo.entity.repository.TransaksiRepository;
import com.webinar.unindra.demo.service.TransaksiService;
import com.webinar.unindra.demo.wrapper.TransaksiWrapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransaksiServiceImpl implements TransaksiService {

    private final TransaksiRepository transaksiRepository;
    private final CustomerRepository customerRepository;
    private final PaketRepository paketRepository;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TransaksiServiceImpl(TransaksiRepository transaksiRepository, CustomerRepository customerRepository, PaketRepository paketRepository) {
        this.transaksiRepository = transaksiRepository;
        this.customerRepository = customerRepository;
        this.paketRepository = paketRepository;
    }

    private TransaksiWrapper toWrapper(Transaksi entity){
        TransaksiWrapper wrapper = new TransaksiWrapper();
        wrapper.setId(entity.getId());
        wrapper.setDescription(entity.getDescription());
        wrapper.setDeleted(entity.getDeleted());
        wrapper.setVersion(entity.getVersion());
        wrapper.setUpdatedDate(entity.getModifiedDate());

        wrapper.setKodeTransaksi("TRS-"+entity.getId());
        wrapper.setJumlah(entity.getJumlah());
        wrapper.setTotalHarga(entity.getTotalHarga());

        wrapper.setIdCustomer(entity.getCustomer().getId());
        wrapper.setNamaCustomer(entity.getCustomer().getNama());

        wrapper.setIdPaket(entity.getPaket().getId());
        wrapper.setNamaPaket(entity.getPaket().getNamaPaket());

        return wrapper;
    }

    private Transaksi toEntity(TransaksiWrapper wrapper){
        Transaksi entity = new Transaksi();
        if (wrapper.getId() != null){
            Optional<Transaksi> optional = transaksiRepository.findById(wrapper.getId());
            if (optional.isPresent()){
                entity = optional.get();
            }
        }
        entity.setDescription(wrapper.getDescription());
        entity.setDeleted(wrapper.getDeleted());
        entity.setVersion(wrapper.getVersion());
        entity.setModifiedDate(wrapper.getUpdatedDate());

        entity.setJumlah(wrapper.getJumlah());
        entity.setTotalHarga(wrapper.getTotalHarga());

        if (wrapper.getIdCustomer() != null){
            Optional<Customer> optional = customerRepository.findById(wrapper.getIdCustomer());
            if (optional.isPresent()){
                entity.setCustomer(optional.get());
            }
        }
        if (wrapper.getIdPaket() != null){
            Optional<Paket> optional = paketRepository.findById(wrapper.getIdPaket());
            if (optional.isPresent()){
                entity.setPaket(optional.get());
            }
        }
        return entity;
    }

    private List<TransaksiWrapper> toWrapperList(List<Transaksi> modelList){
        List<TransaksiWrapper> rList = new ArrayList<>();
        if (modelList != null && !modelList.isEmpty()){
            for (Transaksi entity : modelList){
                rList.add(toWrapper(entity));
            }
        }
        return rList;
    }

    private Page<Transaksi> getFilterByDate(String startDate, String endDate, Pageable pageable) throws ParseException {

        Date startDay = simpleDateFormat.parse(startDate);
        Date endDay = simpleDateFormat.parse(endDate);
        return transaksiRepository.getPageableByDate(startDay, endDay, pageable);
    }

    @Override
    public Page<TransaksiWrapper> getPageableByDate(String startDate, String endDate, int startPage, int pageSize, Sort sort) throws StudyException, ParseException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Transaksi> transaksiPage = getFilterByDate(startDate, endDate, pageable);
        List<TransaksiWrapper> transaksiWrappers =toWrapperList(transaksiPage.getContent());
        return new PageImpl<>(transaksiWrappers, pageable, transaksiPage.getTotalElements());
    }

    @Override
    public Long getNum() {
        return transaksiRepository.count();
    }

    @Override
    public TransaksiWrapper save(TransaksiWrapper wrapper) throws StudyException {
        wrapper.setVersion(1);
        wrapper.setDeleted(false);
        return toWrapper(transaksiRepository.save(toEntity(wrapper)));
    }

    @Override
    public TransaksiWrapper getById(Long pk) throws StudyException {
        Optional<Transaksi> optional = transaksiRepository.findById(pk);
        return optional.map(this::toWrapper).orElse(null);
    }

    @Override
    public Boolean delete(Long pk) throws StudyException {
        try {
            transaksiRepository.deleteById(pk);
            return true;
        } catch (Exception e){
            throw new StudyException(e, ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public List<TransaksiWrapper> getAll() throws StudyException {
        return toWrapperList((List<Transaksi>) transaksiRepository.findAll());
    }

    @Override
    public void deleteAll() throws StudyException {
        //Not Implement yet

    }

    @Override
    public Page<TransaksiWrapper> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        if (transaksiRepository.count() == 0){
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        } else {
            Page<Transaksi> transaksiPage = transaksiRepository.getPageable(sSearch, pageable);
            List<TransaksiWrapper> paketWrapperList = toWrapperList(transaksiPage.getContent());
            return new PageImpl<>(paketWrapperList, pageable, transaksiPage.getTotalElements());
        }
    }
}
