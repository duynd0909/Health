package HealthDeclaration.common.base.entity;

import HealthDeclaration.common.base.constant.BaseEntityConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = BaseEntityConstant.COLUMN_ID)
	private Long id;

	@Column(name = BaseEntityConstant.COLUMN_CREATED_BY,nullable = false)
	private String createdBy;

	@Column(name = BaseEntityConstant.COLUMN_CREATED_TIME,nullable = false)
	private Date createdTime;
	
	@Column(name = BaseEntityConstant.COLUMN_MODIFIED_BY,nullable = false)
	private String modifiedBy;
	
	@Column(name = BaseEntityConstant.COLUMN_MODIFIED_TIME,nullable = false)
	private Date modifiedTime;
	
}
