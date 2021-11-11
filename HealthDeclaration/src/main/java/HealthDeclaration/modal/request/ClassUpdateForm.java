package HealthDeclaration.modal.request;

import lombok.Data;

@Data
public class ClassUpdateForm {
    private Long id;
    private String className;
    private String teacherUsername;
}
