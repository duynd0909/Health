package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.modal.request.ProvinceAddRequest;
import HealthDeclaration.service.IProvinceService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
@Log4j2
@CrossOrigin(origins = "http://localhost:8181")
public class ProvinceController {
    @Autowired
    IProvinceService service;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody List<ProvinceAddRequest> list) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            service.add(list);
            responseMessage.setData("Add success!!!");
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping("/getAll")
    public ResponseEntity add() {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(service.getAll());
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity getByName(@RequestParam("provinceName") String provinceName) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(service.getByName(provinceName));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
