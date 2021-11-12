package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class ReportPermission extends BaseEntity {
    @Column(name = "user_name")
    private String username;
    @Column(name = "class_id")
    private Long classId;
    @Column(name = "permission_type")
    private String permissionType;
}
