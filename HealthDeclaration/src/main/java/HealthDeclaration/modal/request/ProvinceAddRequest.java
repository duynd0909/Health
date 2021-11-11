package HealthDeclaration.modal.request;

import lombok.Data;

@Data
public class ProvinceAddRequest {
    private Long code;
    private String name;
    private String division_type;
    private String codename;
    private String phone_code;
}
