package HealthDeclaration.modal.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    Long index;

    Long id;

    String roleCode;

    String roleName;

    public RoleDTO(Long id, String roleCode, String roleName) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }
}
