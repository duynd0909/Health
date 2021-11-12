package HealthDeclaration.form;

import lombok.Data;

@Data
public class AllowViewReportForm {
    private Long userId;
    private Boolean teacher;
    private Boolean student;
    private Boolean teacherChecked;
    private Boolean studentChecked;
    private Long classId;
}
