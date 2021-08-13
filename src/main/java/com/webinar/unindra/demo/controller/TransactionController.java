package com.webinar.unindra.demo.controller;

import com.webinar.unindra.demo.common.controller.BaseController;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.response.CommonResponses;
import com.webinar.unindra.demo.common.response.CustomReturn;
import com.webinar.unindra.demo.service.TransactionService;
import com.webinar.unindra.demo.wrapper.PackageWrapper;
import com.webinar.unindra.demo.wrapper.TransactionWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/webinar/transaction")
public class TransactionController extends BaseController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/list")
    public CustomReturn<List<TransactionWrapper>> getList() throws StudyException {
        CommonResponses<List<TransactionWrapper>> commonResponses = new CommonResponses<>();
        List<TransactionWrapper> transactionWrappers = transactionService.getAll();
        if (transactionWrappers != null){
            return commonResponses.commonSuccessResponse(transactionWrappers);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/id/{id}")
    public CustomReturn<TransactionWrapper> getId(@PathVariable Long id) throws StudyException{
        CommonResponses<TransactionWrapper> commonResponses = new CommonResponses<>();
        try {
            TransactionWrapper wrapper = transactionService.getById(id);
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
    public CustomReturn<TransactionWrapper> save(@RequestBody TransactionWrapper wrapper) throws StudyException{
        CommonResponses<TransactionWrapper> commonResponses = new CommonResponses<>();
        TransactionWrapper transactionWrapper = transactionService.save(wrapper);
        if (wrapper != null){
            return commonResponses.commonSuccessResponse(transactionWrapper);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @PostMapping(value = "/delete/{id}")
    public CustomReturn<TransactionWrapper> delete(@PathVariable Long id) throws StudyException{
        CommonResponses<TransactionWrapper> commonResponses = new CommonResponses<>();
        boolean result = transactionService.delete(id);
        if (result){
            return commonResponses.commonDeleteSuccess();
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/getPageableByDate")
    public CustomReturn<Page<TransactionWrapper>> getPageableByDate(String startDate, String endDate, Integer pageSize, Integer startPage, String sortBy, String sortOrder) throws StudyException, ParseException {
        CommonResponses<Page<TransactionWrapper>> commonResponses = new CommonResponses<>();
        Sort sort = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
        Page<TransactionWrapper> wrappers = transactionService.getPageableByDate(startDate, endDate, startPage, pageSize, sort);
        try {
            if (wrappers != null){
                return commonResponses.commonSuccessResponse(wrappers);
            } else {
                return commonResponses.commonFailedResponse();
            }
        } catch (Exception e){
            return commonResponses.commonFailedError();
        }
    }

}
