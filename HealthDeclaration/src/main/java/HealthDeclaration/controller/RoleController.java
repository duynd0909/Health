package HealthDeclaration.controller;

import HealthDeclaration.common.base.controller.BaseController;
import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.modal.dto.RoleDTO;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.service.IRoleService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
@Log4j2
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/get-all")
    private ResponseEntity getAll() {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            List<RoleDTO> result = roleService.getAll();
            Map<String, Object> results = new HashMap<>();
            results.put("items", result);
            responseMessage.setData(results);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

    @GetMapping("/get-role-not-student")
    private ResponseEntity getRoleNotStudent() {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            responseMessage.setSuccess(true);
            List<RoleDTO> result = roleService.getRoleNotStudent();
            Map<String, Object> results = new HashMap<>();
            results.put("items", result);
            responseMessage.setData(results);
        } catch (Exception e) {
            log.error(e);
            responseMessage.setSuccess(false);
            return ResponseUtils.buildResponseMessage(false, responseMessage);
        }
        return ResponseUtils.buildResponseMessage(true, responseMessage);
    }

}
