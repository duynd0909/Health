package HealthDeclaration.controller;


import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.DistrictAddForm;
import HealthDeclaration.modal.dto.DistrictDTO;
import HealthDeclaration.modal.dto.WardDTO;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.qrcode.ZXingHelper;
import HealthDeclaration.service.IDistrictService;
import HealthDeclaration.service.IUserService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/district")
@Log4j2
public class DistrictController {

    @Autowired
    private IDistrictService districtService;

    @PostMapping("/add-new-district")
    public ResponseEntity addListDistrict(@RequestBody List<DistrictAddForm> formList) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            districtService.addListDistrict(formList);
            responseMessage.setSuccess(true);
            responseMessage.setData(true);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping("/get-district-by-province")
    public ResponseEntity getDistrictByProvinceAndDistrictName(@RequestParam Long provinceCode, @RequestParam String districtName) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<DistrictDTO> districtDTOS = districtService.getDistrictByProvinceAndDistrictName(provinceCode, districtName);
            responseMessage.setSuccess(true);
            responseMessage.setData(districtDTOS);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
