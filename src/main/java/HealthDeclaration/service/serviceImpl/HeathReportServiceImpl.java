package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.HealthAddForm;
import HealthDeclaration.modal.dto.HealthReportDTO;
import HealthDeclaration.modal.entity.HealthReport;
import HealthDeclaration.modal.request.UserUpdateForm;
import HealthDeclaration.repository.HealthReportRepository;
import HealthDeclaration.service.HealthReportService;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HeathReportServiceImpl extends BaseService implements HealthReportService {

    @Autowired
    private HealthReportRepository healthReportRepository;

    @Autowired
    private IUserService userService;

	@Override
	@Transactional
	public boolean add(HealthAddForm form) {
		try {
			String username = getLoggedInUsername();
			HealthReport report = new HealthReport();
			report.setCreatedBy(username);
			report.setModifiedBy(username);
			report.setModifiedTime(new Date());
			report.setCreatedTime(new Date());

			report.setUsername(username);
			report.setFullName(form.getFullName());
			report.setVerificationId(form.getVerificationId());
			report.setGender(form.getGender());
			report.setDateOfBirth(form.getDateOfBirth());
			report.setPhoneNumber(form.getPhoneNumber());
			report.setHealthInsuranceId(form.getHealthInsuranceId());
			report.setEmail(form.getEmail());
			report.setProvinceCode(form.getProvinceCode());
			report.setDistrictCode(form.getDistrictCode());
			report.setWardCode(form.getWardCode());
			report.setAddressDetail(form.getAddressDetail());

			report.setContactToPlace(form.getContactToPlace());
			report.setSicking(form.getSicking());
			report.setCloseToRiskingPeople(form.getCloseToRiskingPeople());
			report.setCloseToCountry(form.getCloseToCountry());
			report.setCloseToSicking(form.getCloseToSicking());
			report.setFactor(form.getFactor());
			healthReportRepository.save(report);

			// Update user infor
            UserUpdateForm user = new UserUpdateForm();
            user.setUsername(username);
			user.setFullName(form.getFullName());
			user.setGender(report.getGender());
			user.setDob(report.getDateOfBirth());
			user.setPhoneNumber(report.getPhoneNumber());
			user.setHealthInsuranceId(report.getHealthInsuranceId());
			user.setGmail(report.getEmail());
			user.setProvinceCode(report.getProvinceCode());
			user.setDistrictCode(report.getDistrictCode());
			user.setWardCode(report.getWardCode());
			user.setAddressDetail(report.getAddressDetail());
			userService.update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public List<HealthReportDTO> getReportsByUsername(String username){
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -14);
			Date dateBefore14Days = cal.getTime();
			return healthReportRepository.getReportsByUsername(username,dateBefore14Days);
		}catch (Exception e){
			return null;
		}
	}
}
