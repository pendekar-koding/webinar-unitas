package com.webinar.unindra.demo.service.impl;

import com.webinar.unindra.demo.common.exception.ErrorCode;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.message.DataTableObject;
import com.webinar.unindra.demo.entity.Package;
import com.webinar.unindra.demo.entity.repository.PackageRepository;
import com.webinar.unindra.demo.service.PackageService;
import com.webinar.unindra.demo.wrapper.PackageWrapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    private PackageWrapper toWrapper(Package entity){
        PackageWrapper wrapper = new PackageWrapper();
        wrapper.setId(entity.getId());
        wrapper.setDescription(entity.getDescription());
        wrapper.setDeleted(entity.getDeleted());
        wrapper.setVersion(entity.getVersion());

        wrapper.setName(entity.getName());
        wrapper.setPrice(entity.getPrice());

        return wrapper;
    }

    private Package toEntity(PackageWrapper wrapper){
        Package entity = new Package();
        if (wrapper.getId() != null){
            Optional<Package> optional = packageRepository.findById(wrapper.getId());
            if (optional.isPresent()){
                entity = optional.get();
            }
        }
        entity.setDescription(wrapper.getDescription());
        entity.setDeleted(wrapper.getDeleted());
        entity.setVersion(wrapper.getVersion());

        entity.setName(wrapper.getName());
        entity.setPrice(wrapper.getPrice());

        return entity;
    }

    private List<PackageWrapper> toWrapperList(List<Package> modelList){
        List<PackageWrapper> rList = new ArrayList<>();
        if (modelList != null && !modelList.isEmpty()){
            for (Package entity : modelList){
                rList.add(toWrapper(entity));
            }
        }
        return rList;
    }

    @Override
    public Long getNum() {
        return packageRepository.count();
    }

    @Override
    public PackageWrapper save(PackageWrapper wrapper) throws StudyException {
        wrapper.setDeleted(false);
        wrapper.setVersion(1);
        return toWrapper(packageRepository.save(toEntity(wrapper)));
    }

    @Override
    public PackageWrapper getById(Long pk) throws StudyException {
        Optional<Package> optional = packageRepository.findById(pk);
        return optional.map(this::toWrapper).orElse(null);
    }

    @Override
    public Boolean delete(Long pk) throws StudyException {
        try {
            packageRepository.deleteById(pk);
            return true;
        } catch (Exception e){
            throw new StudyException(e, ErrorCode.GENERIC_FAILURE);
        }
    }

    @Override
    public List<PackageWrapper> getAll() throws StudyException {
        return toWrapperList((List<Package>) packageRepository.findAll());
    }

    @Override
    public void deleteAll() throws StudyException {
        //Not Implement yet

    }

    @Override
    public Page<PackageWrapper> getPageableList(String sSearch, int startPage, int pageSize, Sort sort) throws StudyException {
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        if (packageRepository.count() == 0){
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        } else {
            Page<Package> paketPage = packageRepository.getPageable(sSearch, pageable);
            List<PackageWrapper> packageWrapperList = toWrapperList(paketPage.getContent());
            return new PageImpl<>(packageWrapperList, pageable, paketPage.getTotalElements());
        }
    }
}
