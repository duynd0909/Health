package HealthDeclaration.form;

import lombok.Data;

import java.util.Date;

@Data
public class HealthReportAddForm {
    private String username;
    private String fullName;
    private String verificationId;
    private Boolean gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String healthInsuranceId;
    private String gmail;

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
