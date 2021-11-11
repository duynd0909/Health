package HealthDeclaration.form;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserFormSearch {
    String fullName;

    String userName;

    String gender;

    Boolean genderSearch;

    String wardName;

    String districtName;

    String provinceName;

    Long classID;

    String roleCode;
}
