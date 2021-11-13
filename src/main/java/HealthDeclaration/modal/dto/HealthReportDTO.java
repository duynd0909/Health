package HealthDeclaration.modal.dto;

import HealthDeclaration.common.base.dto.BaseDTO;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthReportDTO {
    private Long id;

    private String createdBy;

    private Date createdTime;

    private String modifiedBy;

    private Date modifiedTime;
    private String username;
    private String fullName;
    private String verificationId;
    private Boolean gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String healthInsuranceId;
    private String email;
    private Long provinceCode;
    private Long districtCode;
    private Long wardCode;
    private String addressDetail;
    private Boolean closeToSicking;
    private Boolean closeToCountry;
    private Boolean closeToRiskingPeople;
    private Boolean contactToPlace;
    private Boolean sicking;
    private String provinceName;
    private String districtName;
    private String wardName;
    private String factor;

}
