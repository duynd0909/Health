package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.HealthReportAddForm;
import HealthDeclaration.modal.dto.HealthFormDto;
import HealthDeclaration.service.ITrackingReportService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tracking-report")
@Log4j2
public class TrackingReportController {
    @Autowired
    ITrackingReportService service;

    @GetMapping(value = "/search-health-form-report")
    public ResponseEntity searchHealthFormReport(@RequestParam Long userId
            , @RequestParam("pageIndex") int pageIndex
            , @RequestParam("pageSize") int pageSize) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            List<HealthFormDto> result = service.searchHealthFormReport(userId, pageIndex, pageSize);
            Long total = service.countSearchHealthFormReport(userId);
            Map<String, Object> results = new HashMap<>();
            results.put("items", result);
            results.put("total", total);
            responseMessage.setData(results);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody HealthReportAddForm formReportAdd) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            responseMessage.setData(service.add(formReportAdd));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping(value = "/getById")
    public ResponseEntity getOne(@RequestParam Long id) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            responseMessage.setData(service.getById(id));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping(value = "/get-list-by-username")
    public ResponseEntity getTrackingReportByUsername(@RequestParam String username) {
        ResponseMessage responseMessage = new ResponseMessage();
        try{
            responseMessage.setSuccess(true);
            responseMessage.setData(service.getTrackingReportByUsername(username));
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
