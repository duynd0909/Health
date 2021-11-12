package HealthDeclaration.modal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;

@Data
@Entity
public class TrackingReport extends BaseEntity {
    private String username;
    private String fullName;
    private String verificationId;
    private Boolean gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String healthInsuranceId;
    private String email;

    // Dia chi ca nhan
    private Long provinceCode;
    private Long districtCode;
    private Long wardCode;
    private String addressDetail;

    // Thong tin di chuyen
    private String vehicleType;
    private String controlPlateNumber;
    private Date movingDate;

    // Di chuyen tu
    private Long provinceCodeFrom;
    private Long districtCodeFrom;
    private Long wardCodeFrom;
    private String addressDetailFrom;

    // Di chuyen toi
    private Long provinceCodeTo;
    private Long districtCodeTo;
    private Long wardCodeTo;
    private String addressDetailTo;

    // Tiep xuc
    private Boolean contactToPlace;
    private Boolean sicking;
    private Boolean closeToRiskingPeople;
    private Boolean closeToCountry;
    private Boolean closeToSicking;
}
