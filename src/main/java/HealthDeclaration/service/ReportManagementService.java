package HealthDeclaration.service;

import HealthDeclaration.form.AllowViewReportForm;
import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;

import java.util.List;

public interface ReportManagementService {
    /**
     *
     * @param form
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<UserDto> searchStudent(UserFormSearch form, int pageIndex, int pageSize);

    /**
     *
     * @param form
     * @return
     */
    Long countStudent(UserFormSearch form);

    /**
     *
     * @param form
     * @return
     */
    boolean updateAllowViewReport(AllowViewReportForm form);
}
