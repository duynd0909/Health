package HealthDeclaration.common.base.dto;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class BaseDTO {
    private Long id;

    private String createdBy;

    private Date createdTime;

    private String modifiedBy;

    private Date modifiedTime;
}
