package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Province extends BaseEntity {
    private Boolean deleted;
    private Long code;
    private String name;
    private String divisionType;
    private String codeName;
    private String phoneCode;
}
