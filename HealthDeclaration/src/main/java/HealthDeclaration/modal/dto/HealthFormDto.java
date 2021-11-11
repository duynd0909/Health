package HealthDeclaration.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthFormDto {
    private Long index;
    private Long id;
    private Long userId;
    private String reportType;
    private String status;
    private Date createdTime;

    public HealthFormDto(Long id, Date createdTime, Long userId, String reportType, String status) {
        this.id = id;
        this.userId = userId;
        this.reportType = reportType;
        this.status = status;
        this.createdTime = createdTime;
    }
}

