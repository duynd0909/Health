package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class District extends BaseEntity {

    @Column(name = "deleted",nullable = true)
    private Boolean deleted;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private Long code;

    @Column(name = "division_type")
    private String divisionType;

    @Column(name = "codename")
    private String codename;

    @Column(name = "province_code")
    private Long provinceCode;

}
