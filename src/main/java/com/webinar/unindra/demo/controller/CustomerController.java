package com.webinar.unindra.demo.controller;

import com.webinar.unindra.demo.common.controller.BaseController;
import com.webinar.unindra.demo.common.exception.StudyException;
import com.webinar.unindra.demo.common.response.CommonResponses;
import com.webinar.unindra.demo.common.response.CustomReturn;
import com.webinar.unindra.demo.service.CustomerService;
import com.webinar.unindra.demo.wrapper.CustomerWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/webinar/customer")
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/list")
    public CustomReturn<List<CustomerWrapper>> getListCustomer() throws StudyException{
        CommonResponses<List<CustomerWrapper>> commonResponses = new CommonResponses<>();
        List<CustomerWrapper> customerWrappers = customerService.getAll();
        if (customerWrappers != null){
            return commonResponses.commonSuccessResponse(customerWrappers);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @GetMapping(value = "/id/{id}")
    public CustomReturn<CustomerWrapper> getById(@PathVariable Long id) throws StudyException{
        CommonResponses<CustomerWrapper> commonResponses = new CommonResponses<>();
        try {
            CustomerWrapper wrapper = customerService.getById(id);
            if (wrapper != null){
                return commonResponses.commonSuccessResponse(wrapper);
            }else {
                return commonResponses.commonFailedResponse();
            }
        } catch (Exception e){
            return commonResponses.commonFailedError();
        }
    }

    @PostMapping(value = "/save")
    public CustomReturn<CustomerWrapper> save(@RequestBody CustomerWrapper wrapper) throws StudyException{
        CommonResponses<CustomerWrapper> commonResponses = new CommonResponses<>();
        CustomerWrapper customerWrapper = customerService.save(wrapper);
        if (wrapper != null){
            return commonResponses.commonSuccessResponse(customerWrapper);
        } else {
            return commonResponses.commonFailedResponse();
        }
    }

    @PostMapping(value = "/delete/{id}")
    public CustomReturn<CustomerWrapper> deleteById(@PathVariable Long id) throws StudyException{
        CommonResponses<CustomerWrapper> commonResponses = new CommonResponses<>();
        boolean result = customerService.delete(id);
        if (result){
            return commonResponses.commonDeleteSuccess();
        } else {
            return commonResponses.commonFailedResponse();
        }
    }
}
