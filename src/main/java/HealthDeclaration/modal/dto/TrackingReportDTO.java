package HealthDeclaration.modal.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingReportDTO {
    private Date createdTime;

    private String username;
    private String fullName;
    private String verificationId;
    private Boolean gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String healthInsuranceId;
    private String email;

    // Dia chi ca nhan
    private String provinceName;
    private String districtName;
    private String wardName;
    private String addressDetail;

    // Thong tin di chuyen
    private String vehicleType;
    private String controlPlateNumber;
    private Date movingDate;

    // Di chuyen tu
    private String provinceNameFrom;
    private String districtNameFrom;
    private String wardNameFrom;
    private String addressDetailFrom;

    // Di chuyen toi
    private String provinceNameTo;
    private String districtNameTo;
    private String wardNameTo;
    private String addressDetailTo;

    // Tiep xuc
    private Boolean contactToPlace;
    private Boolean sicking;
    private Boolean closeToRiskingPeople;
    private Boolean closeToCountry;
    private Boolean closeToSicking;
}
