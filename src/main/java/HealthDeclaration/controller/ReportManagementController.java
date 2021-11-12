package HealthDeclaration.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.modal.dto.HealthReportDTO;
import HealthDeclaration.modal.dto.RoleDTO;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.JwtRequest;
import HealthDeclaration.service.HealthReportService;
import HealthDeclaration.service.ReportManagementService;
import HealthDeclaration.vo.ResponseMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@Log4j2
@CrossOrigin(origins = "http://localhost:8181")
public class ReportManagementController {

    @Autowired
    private ReportManagementService reportManagementService;

    @Autowired
    private HealthReportService healthReportService;


}
