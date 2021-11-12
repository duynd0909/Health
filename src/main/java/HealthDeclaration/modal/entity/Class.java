package HealthDeclaration.modal.entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import HealthDeclaration.common.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Class extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "teacher_username")
    private String teacherUsername;
}
