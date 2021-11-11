package HealthDeclaration.form;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WardAddForm {
    String name;
    Long code;
    String division_type;
    String codename;
    Long district_code;
}