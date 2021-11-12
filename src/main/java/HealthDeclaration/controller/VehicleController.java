package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.WardAddForm;
import HealthDeclaration.modal.dto.VehicleDTO;
import HealthDeclaration.service.IVehicleService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicle")
@Log4j2
public class VehicleController {
    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("/get-vehicle-by-name")
    public ResponseEntity getVehicleByName(@RequestParam("vehicleName") String vehicleName) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<VehicleDTO> result = vehicleService.getVehicleByName(vehicleName);
            Map<String, Object> results = new HashMap<>();
            results.put("items", result);
            responseMessage.setSuccess(true);
            responseMessage.setData(results);
        } catch (Exception e) {
            responseMessage.setSuccess(false);
            return  ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return  ResponseUtils.buildResponseMessage(true, responseMessage);
    }
}
