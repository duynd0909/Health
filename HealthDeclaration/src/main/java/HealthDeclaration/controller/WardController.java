package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.WardAddForm;
import HealthDeclaration.modal.dto.WardDTO;
import HealthDeclaration.service.IWardService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ward")
@Log4j2
public class WardController {

    @Autowired
    private IWardService wardService;

    @PostMapping("/add-new-ward")
    public ResponseEntity addListward(@RequestBody List<WardAddForm> formList) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            wardService.addListward(formList);
            responseMessage.setSuccess(true);
            responseMessage.setData(true);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping("/get-ward-by-district")
    public ResponseEntity getWardByDistrict(@RequestParam Long districtCode, @RequestParam String wardName) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<WardDTO> wardDtoList = wardService.getWardByDistrictCodeAndWardName(districtCode, wardName);
            responseMessage.setSuccess(true);
            responseMessage.setData(wardDtoList);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
