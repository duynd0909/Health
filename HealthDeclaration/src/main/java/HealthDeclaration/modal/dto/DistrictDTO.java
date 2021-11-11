package HealthDeclaration.modal.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDTO {

    private Long id;

    private Long districtCode;

    private String districtName;

    private Long provinceCode;
}
