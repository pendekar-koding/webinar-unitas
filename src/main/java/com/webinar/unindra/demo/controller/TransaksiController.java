package com.webinar.unindra.demo.controller;

import com.webinar.unindra.demo.common.controller.BaseController;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.response.CommonResponses;
import com.webinar.unindra.demo.common.response.CustomReturn;
import com.webinar.unindra.demo.service.TransaksiService;
import com.webinar.unindra.demo.wrapper.TransaksiWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/webinar/transaksi")
public class TransaksiController extends BaseController {

    private final TransaksiService transaksiService;

    public TransaksiController(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
    }

    @GetMapping(value = "/list")
    public CustomReturn<List<TransaksiWrapper>> getList() throws StudyException {
        CommonResponses<List<TransaksiWrapper>> commonResponses = new CommonResponses<>();
        List<TransaksiWrapper> transaksiWrappers = transaksiService.getAll();
        if (transaksiWrappers != null){
            return commonResponses.commonSuccessResponse(transaksiWrappers);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/id/{id}")
    public CustomReturn<TransaksiWrapper> getId(@PathVariable Long id) throws StudyException{
        CommonResponses<TransaksiWrapper> commonResponses = new CommonResponses<>();
        try {
            TransaksiWrapper wrapper = transaksiService.getById(id);
            if (wrapper != null){
                return commonResponses.commonSuccessResponse(wrapper);
            } else {
                return commonResponses.commonFailedResponse();
            }
        } catch (Exception e){
            return commonResponses.commonFailedError();
        }
    }

    @PostMapping(value = "/save")
    public CustomReturn<TransaksiWrapper> save(@RequestBody TransaksiWrapper wrapper) throws StudyException{
        CommonResponses<TransaksiWrapper> commonResponses = new CommonResponses<>();
        TransaksiWrapper transaksiWrapper = transaksiService.save(wrapper);
        if (wrapper != null){
            return commonResponses.commonSuccessResponse(transaksiWrapper);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @PostMapping(value = "/delete/{id}")
    public CustomReturn<TransaksiWrapper> delete(@PathVariable Long id) throws StudyException{
        CommonResponses<TransaksiWrapper> commonResponses = new CommonResponses<>();
        boolean result = transaksiService.delete(id);
        if (result){
            return commonResponses.commonDeleteSuccess();
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/pageableList")
    public CustomReturn<Page<TransaksiWrapper>> pageableList(String sSearch, Integer pageSize, Integer startPage, String sortBy, String sortOrder) throws StudyException{
        CommonResponses<Page<TransaksiWrapper>> commonResponses = new CommonResponses<>();
        Sort sort = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
        Page<TransaksiWrapper>wrapperPage = transaksiService.getPageableList(sSearch, startPage, pageSize, sort);
        try {
            if (wrapperPage != null){
                return commonResponses.commonSuccessResponse(wrapperPage);
            } else {
                return commonResponses.commonFailedResponse();
            }
        } catch (Exception e){
            return commonResponses.commonFailedError();
        }
    }

    @GetMapping(value = "/pageableByDate")
    public CustomReturn<Page<TransaksiWrapper>> pageableByDate(String startDate, String endDate, Integer pageSize, Integer startPage, String sortBy, String sortOrder) throws StudyException, ParseException {
        CommonResponses<Page<TransaksiWrapper>> commonResponses = new CommonResponses<>();
        Sort sort = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
        Page<TransaksiWrapper>wrapperPage = transaksiService.getPageableByDate(startDate, endDate, startPage, pageSize, sort);
        try {
            if (wrapperPage != null){
                return commonResponses.commonSuccessResponse(wrapperPage);
            } else {
                return commonResponses.commonFailedResponse();
            }
        } catch (Exception e){
            return commonResponses.commonFailedError();
        }
    }

}
