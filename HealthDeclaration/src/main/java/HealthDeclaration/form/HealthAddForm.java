package HealthDeclaration.form;

import java.util.Date;

import lombok.Data;
@Data
public class HealthAddForm {
	private String username;
	private String fullName;
	private String verificationId;
	private Boolean gender;
	private Date dateOfBirth;
	private String phoneNumber;
	private String healthInsuranceId;
	private String email;
	private Long provinceCode;
	private Long districtCode;
	private Long wardCode;
	private String addressDetail;

	private Boolean contactToPlace;
	private Boolean sicking;
	private Boolean closeToRiskingPeople;
	private Boolean closeToCountry;
	private Boolean closeToSicking;

}
