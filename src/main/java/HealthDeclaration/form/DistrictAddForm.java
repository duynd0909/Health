package HealthDeclaration.form;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictAddForm {
    String name;
    Long code;
    String division_type;
    String codename;
    Long province_code;
}
