package HealthDeclaration.service;

import HealthDeclaration.form.HealthAddForm;
import HealthDeclaration.modal.dto.HealthReportDTO;

import java.util.List;

public interface HealthReportService {

	/**
	 *
	 * @param form
	 * @return
	 */
	boolean add(HealthAddForm form);

	/**
	 *
	 * @param username
	 * @return
	 */
	List<HealthReportDTO> getReportsByUsername(String username);
}
