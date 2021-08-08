package com.webinar.unindra.demo.controller;

import com.webinar.unindra.demo.common.controller.BaseController;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.response.CommonResponses;
import com.webinar.unindra.demo.common.response.CustomReturn;
import com.webinar.unindra.demo.service.PackageService;
import com.webinar.unindra.demo.wrapper.PackageWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/webinar/package")
public class PackageController extends BaseController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping(value = "/list")
    public CustomReturn<List<PackageWrapper>> getList() throws StudyException{
        CommonResponses<List<PackageWrapper>> commonResponses = new CommonResponses<>();
        List<PackageWrapper> packageWrappers = packageService.getAll();
        if (packageWrappers != null){
            return commonResponses.commonSuccessResponse(packageWrappers);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/id/{id}")
    public CustomReturn<PackageWrapper> getId(@PathVariable Long id) throws StudyException{
        CommonResponses<PackageWrapper> commonResponses = new CommonResponses<>();
        try {
            PackageWrapper wrapper = packageService.getById(id);
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
    public CustomReturn<PackageWrapper> save(@RequestBody PackageWrapper wrapper) throws StudyException{
        CommonResponses<PackageWrapper> commonResponses = new CommonResponses<>();
        PackageWrapper packageWrapper = packageService.save(wrapper);
        if (wrapper != null){
            return commonResponses.commonSuccessResponse(packageWrapper);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @PostMapping(value = "/delete/{id}")
    public CustomReturn<PackageWrapper> delete(@PathVariable Long id) throws StudyException{
        CommonResponses<PackageWrapper> commonResponses = new CommonResponses<>();
        boolean result = packageService.delete(id);
        if (result){
            return commonResponses.commonDeleteSuccess();
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/pageableList")
    public CustomReturn<Page<PackageWrapper>> pageableList(String sSearch, Integer pageSize, Integer startPage, String sortBy, String sortOrder) throws StudyException{
        CommonResponses<Page<PackageWrapper>> commonResponses = new CommonResponses<>();
        Sort sort = new Sort(Sort.Direction.fromString(sortOrder), sortBy);
        Page<PackageWrapper>wrapperPage = packageService.getPageableList(sSearch, startPage, pageSize, sort);
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
