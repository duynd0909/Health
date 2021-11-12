package HealthDeclaration.modal.entity;

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
public class Ward extends BaseEntity {

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

    @Column(name = "district_code")
    private Long districtCode;
}
