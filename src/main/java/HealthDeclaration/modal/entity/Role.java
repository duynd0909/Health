package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role extends BaseEntity {

    private String roleCode;

    private String roleName;
}
