package com.webinar.unindra.demo.service.impl;

import com.webinar.unindra.demo.common.exception.ErrorCode;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.message.DataTableObject;
import com.webinar.unindra.demo.entity.Paket;
import com.webinar.unindra.demo.entity.repository.PaketRepository;
import com.webinar.unindra.demo.service.PaketService;
import com.webinar.unindra.demo.wrapper.PaketWrapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaketServiceImpl implements PaketService {

    private final PaketRepository paketRepository;

    public PaketServiceImpl(PaketRepository paketRepository) {
        this.paketRepository = paketRepository;
    }

    private PaketWrapper toWrapper(Paket entity){
        PaketWrapper wrapper = new PaketWrapper();
        wrapper.setId(entity.getId());
        wrapper.setDescription(entity.getDescription());
        wrapper.setDeleted(entity.getDeleted());
        wrapper.setVersion(entity.getVersion());

        wrapper.setNamaPaket(entity.getNamaPaket());
        wrapper.setHarga(entity.getHarga());

        return wrapper;
    }

    private Paket toEntity(PaketWrapper wrapper){
        Paket entity = new Paket();
        if (wrapper.getId() != null){
            Optional<Paket> optional = paketRepository.findById(wrapper.getId());
            if (optional.isPresent()){
                entity = optional.get();
            }
        }
        entity.setDescription(wrapper.getDescription());
        entity.setDeleted(wrapper.getDeleted());
        entity.setVersion(wrapper.getVersion());

        entity.setNamaPaket(wrapper.getNamaPaket());
        entity.setHarga(wrapper.getHarga());

        return entity;
    }

    private List<PaketWrapper> toWrapperList(List<Paket> modelList){
        List<PaketWrapper> rList = new ArrayList<>();
        if (modelList != null && !modelList.isEmpty()){
            for (Paket entity : modelList){
                rList.add(toWrapper(entity));
            }
        }
        return rList;
    }

    @Override
    public Long getNum() {
        return paketRepository.count();
    }

    @Override
    public PaketWrapper save(PaketWrapper wrapper) throws StudyException {
        wrapper.setDeleted(false);
        wrapper.setVersion(1);
        return toWrapper(paketRepository.save(toEntity(wrapper)));
    }

    @Override
    public PaketWrapper getById(Long pk) throws StudyException {
        Optional<Paket> optional = paketRepository.findById(pk);
        return optional.map(this::toWrapper).orElse(null);
    }

    @Override
    public Boolean delete(Long pk) throws StudyException {
        try {
            paketRepository.deleteById(pk);
            return true;
        } catch (Exception e){
            throw new StudyException(e, ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public List<PaketWrapper> getAll() throws StudyException {
        return toWrapperList((List<Paket>) paketRepository.findAll());
    }

    @Override
    public void deleteAll() throws StudyException {
        //Not Implement yet

    }

    @Override
    public Page<PaketWrapper> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        if (paketRepository.count() == 0){
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        } else {
            Page<Paket> paketPage = paketRepository.getPageable(sSearch, pageable);
            List<PaketWrapper> paketWrapperList = toWrapperList(paketPage.getContent());
            return new PageImpl<>(paketWrapperList, pageable, paketPage.getTotalElements());
        }
    }
}
