package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "gmail")
    private String gmail;

    @Column(name = "health_insurance_id", length = 15)
    private String healthInsuranceId;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "parent_phone_number", length = 10)
    private String parentPhoneNumber;

    @Column(name = "province_code")
    private Long provinceCode;

    @Column(name = "district_code")
    private Long districtCode;

    @Column(name = "ward_code")
    private Long wardCode;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "class_id")
    private Long classID;

    @Column(name = "allow_view_report")
    private Boolean allowViewReport;
}
