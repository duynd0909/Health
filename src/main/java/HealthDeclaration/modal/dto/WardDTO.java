package HealthDeclaration.modal.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WardDTO {

    private Long id;

    private Long wardCode;

    private String wardName;

    private Long districtCode;

}
