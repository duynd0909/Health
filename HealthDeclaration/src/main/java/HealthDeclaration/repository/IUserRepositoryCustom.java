package HealthDeclaration.repository;

import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;

import java.util.List;

public interface IUserRepositoryCustom {

    List<UserDto> searchTeacherByName(String teacherName, String roleCode, int pageIndex, int pageSize);

    List<UserDto> searchStudentToManagement(UserFormSearch formSearch, int pageIndex, int pageSize);

    Long countSearchUserToManagement(UserFormSearch formSearch);

    List<UserDto> searchTeacherToManagement(UserFormSearch formSearch, int pageIndex, int pageSize, String studentRole);

    Long countSearchTeacherToManagement(UserFormSearch formSearch, String roleHocSinh);
}
