package com.webinar.unindra.demo.controller;

import com.webinar.unindra.demo.common.controller.BaseController;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.response.CommonResponses;
import com.webinar.unindra.demo.common.response.CustomReturn;
import com.webinar.unindra.demo.service.PaketService;
import com.webinar.unindra.demo.wrapper.PaketWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/webinar/paket")
public class PaketController extends BaseController {

    private final PaketService paketService;

    public PaketController(PaketService paketService) {
        this.paketService = paketService;
    }

    @GetMapping(value = "/list")
    public CustomReturn<List<PaketWrapper>> getList() throws StudyException{
        CommonResponses<List<PaketWrapper>> commonResponses = new CommonResponses<>();
        List<PaketWrapper> paketWrappers = paketService.getAll();
        if (paketWrappers != null){
            return commonResponses.commonSuccessResponse(paketWrappers);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/id/{id}")
    public CustomReturn<PaketWrapper> getId(@PathVariable Long id) throws StudyException{
        CommonResponses<PaketWrapper> commonResponses = new CommonResponses<>();
        try {
            PaketWrapper wrapper = paketService.getById(id);
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
    public CustomReturn<PaketWrapper> save(@RequestBody PaketWrapper wrapper) throws StudyException{
        CommonResponses<PaketWrapper> commonResponses = new CommonResponses<>();
        PaketWrapper paketWrapper = paketService.save(wrapper);
        if (wrapper != null){
            return commonResponses.commonSuccessResponse(paketWrapper);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @PostMapping(value = "/delete/{id}")
    public CustomReturn<PaketWrapper> delete(@PathVariable Long id) throws StudyException{
        CommonResponses<PaketWrapper> commonResponses = new CommonResponses<>();
        boolean result = paketService.delete(id);
        if (result){
            return commonResponses.commonDeleteSuccess();
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/pageableList")
    public CustomReturn<Page<PaketWrapper>> pageableList(String sSearch, Integer pageSize, Integer startPage, String sortBy, String sortOrder) throws StudyException{
        CommonResponses<Page<PaketWrapper>> commonResponses = new CommonResponses<>();
        Sort sort = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
        Page<PaketWrapper>wrapperPage = paketService.getPageableList(sSearch, startPage, pageSize, sort);
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
