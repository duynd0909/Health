package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.AllowViewReportForm;
import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.repository.ReportManagementRepositoryCustom;
import HealthDeclaration.service.ReportManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportManagementServiceImpl extends BaseService implements ReportManagementService {

    @Autowired
    private ReportManagementRepositoryCustom reportManagementRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<UserDto> searchStudent(UserFormSearch form, int pageIndex, int pageSize) {
        return reportManagementRepository.searchStudent(form, pageIndex, pageSize);
    }

    @Override
    public Long countStudent(UserFormSearch form) {
        return reportManagementRepository.countStudent(form);
    }

    @Override
    public boolean updateAllowViewReport(AllowViewReportForm form) {
        String username = getLoggedInUsername();
        try {
            if (form.getTeacherChecked()) {
                userRepository.findById(form.getUserId()).ifPresent(data -> {
                    data.setModifiedBy(username);
                    data.setModifiedTime(new Date());
                    data.setAllowViewReport(form.getTeacher());
                    userRepository.save(data);
                });
            }
            if (form.getStudentChecked()) {
                List<User> listUser = userRepository.getAllUserByClassId(form.getClassId());
                if (!ObjectUtils.isNullorEmpty(listUser)) {
                    List<User> listUpdated = new ArrayList<>();
                    for (User u : listUser) {
                        u.setModifiedBy(username);
                        u.setModifiedTime(new Date());
                        u.setAllowViewReport(form.getStudent());
                        listUpdated.add(u);
                    }
                    userRepository.saveAll(listUpdated);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
