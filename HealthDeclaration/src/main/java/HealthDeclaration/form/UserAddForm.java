package HealthDeclaration.form;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserAddForm {
    private String username;
    private String password;
    private String fullName;
    private Date dob;
    private Boolean gender;
    private String phoneNumber;
    private String parentPhoneNumber;
    private Long provinceCode;
    private Long districtCode;
    private Long wardCode;
    private String addressDetail;
    private String roleCode;
    private Long classID;
}
