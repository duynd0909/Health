package HealthDeclaration.modal.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    Long index;
    Long id;
    String code;
    String name;

    public VehicleDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
