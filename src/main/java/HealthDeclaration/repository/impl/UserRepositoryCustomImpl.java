package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.constants.RoleConstant;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.repository.IUserRepositoryCustom;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Log4j2
public class UserRepositoryCustomImpl extends BaseRepository implements IUserRepositoryCustom {

    @Override
    public List<UserDto> searchTeacherByName(String teacherName, String roleCode, int pageIndex, int pageSize) {
        TypedQuery<UserDto> query = this.buildSearchTeacher(teacherName , roleCode, UserDto.class);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public List<UserDto> searchStudentToManagement(UserFormSearch formSearch, int pageIndex, int pageSize) {
        TypedQuery<UserDto> query = this.buildSearchStudentToManagement(formSearch, UserDto.class, false);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long countSearchUserToManagement(UserFormSearch formSearch) {
        TypedQuery<Long> query = this.buildSearchStudentToManagement(formSearch, Long.class, true);
        return query.getSingleResult();
    }

    @Override
    public List<UserDto> searchTeacherToManagement(UserFormSearch formSearch, int pageIndex,
                                                   int pageSize, String studentRole) {
        TypedQuery<UserDto> query =
                this.buildSearchTeacherToManagement(formSearch, UserDto.class, false, studentRole);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long countSearchTeacherToManagement(UserFormSearch formSearch, String roleHocSinh) {
        TypedQuery<Long> query = this.buildSearchTeacherToManagement(formSearch, Long.class, true, roleHocSinh);
        return query.getSingleResult();
    }

    private <T> TypedQuery<T> buildSearchStudentToManagement(UserFormSearch formSearch, Class<T> clazz, Boolean count) {
        StringBuilder sql = new StringBuilder();
        if(count) {
            sql.append("select count(u.id) "
                    + "from User u "
                    + " LEFT JOIN Province prv ON prv.code = u.provinceCode "
                    + " LEFT JOIN District dis ON dis.code = u.districtCode "
                    + " LEFT JOIN Ward wa ON wa.code = u.wardCode "
                    + " LEFT JOIN Class cl ON cl.id = u.classID "
                    + " LEFT JOIN Role rl ON rl.roleCode = u.roleCode "
                    + " where 1=1 ");
        } else {
            sql.append("select new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName, u.dob, u.gender," +
                    " u.phoneNumber, u.parentPhoneNumber, u.provinceCode, prv.name as provinceName, u.districtCode, " +
                    " dis.name as districtName, u.wardCode, wa.name as wardName, u.addressDetail, u.roleCode, " +
                    " rl.roleName, u.classID, cl.name, cl.teacherUsername,u.healthInsuranceId ) "
                    + " from User u "
                    + " LEFT JOIN Province prv ON prv.code = u.provinceCode "
                    + " LEFT JOIN District dis ON dis.code = u.districtCode "
                    + " LEFT JOIN Ward wa ON wa.code = u.wardCode "
                    + " LEFT JOIN Class cl ON cl.id = u.classID "
                    + " LEFT JOIN Role rl ON rl.roleCode = u.roleCode "
                    + " where 1=1 ");
        }
        Map<String, Object> params = new HashMap<>();

        sql.append(" and u.roleCode = :roleCode ");
        params.put("roleCode", RoleConstant.ROLE_HOC_SINH);

        if (!ObjectUtils.isNullorEmpty(formSearch.getFullName())) {
            sql.append(" and LOWER(u.fullName) like :studentName ");
            params.put("studentName", "%" + formSearch.getFullName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getUserName())) {
            sql.append(" and LOWER(u.username) like :username ");
            params.put("username", "%" + formSearch.getUserName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getGenderSearch())) {
            sql.append(" and u.gender = :gender ");
            params.put("gender",  formSearch.getGenderSearch());
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getDistrictName())) {
            sql.append(" and LOWER(dis.name) like :districtName ");
            params.put("districtName",  "%" + formSearch.getDistrictName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getProvinceName())) {
            sql.append(" and LOWER(prv.name) like :provinceName ");
            params.put("provinceName",  "%" + formSearch.getProvinceName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getWardName())) {
            sql.append(" and LOWER(wa.name) like :wardName ");
            params.put("wardName", "%" + formSearch.getWardName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getClassID())) {
            sql.append(" and cl.id = :classID");
            params.put("classID", formSearch.getClassID());
        }
        if(!count) {
            sql.append(" ORDER BY u.fullName ASC, u.username ASC");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }

    private <T> TypedQuery<T> buildSearchTeacherToManagement(UserFormSearch formSearch,
                                                             Class<T> clazz, Boolean count, String studentRole) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if(count) {
            sql.append("select count(u.id) "
                    + "from User u "
                    + " LEFT JOIN Province prv ON prv.code = u.provinceCode "
                    + " LEFT JOIN District dis ON dis.code = u.districtCode "
                    + " LEFT JOIN Ward wa ON wa.code = u.wardCode "
                    + " LEFT JOIN Role rl ON rl.roleCode = u.roleCode "
                    + " where 1=1 ");
        } else {
            sql.append("select new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName, u.dob, u.gender," +
                    " u.phoneNumber, u.parentPhoneNumber, u.provinceCode, prv.name as provinceName, u.districtCode, " +
                    " dis.name as districtName, u.wardCode, wa.name as wardName, u.addressDetail, u.roleCode, " +
                    " rl.roleName) "
                    + " from User u "
                    + " LEFT JOIN Province prv ON prv.code = u.provinceCode "
                    + " LEFT JOIN District dis ON dis.code = u.districtCode "
                    + " LEFT JOIN Ward wa ON wa.code = u.wardCode "
                    + " LEFT JOIN Role rl ON rl.roleCode = u.roleCode "
                    + " where 1=1 ");
        }
        sql.append(" and rl.roleCode != :studentRole ");
        params.put("studentRole",  studentRole );

        if (!ObjectUtils.isNullorEmpty(formSearch.getFullName())) {
            sql.append(" and LOWER(u.fullName) like :studentName ");
            params.put("studentName", "%" + formSearch.getFullName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getUserName())) {
            sql.append(" and LOWER(u.username) like :username ");
            params.put("username", "%" + formSearch.getUserName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getGenderSearch())) {
            sql.append(" and u.gender = :gender ");
            params.put("gender",  formSearch.getGenderSearch());
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getDistrictName())) {
            sql.append(" and LOWER(dis.name) like :districtName ");
            params.put("districtName",  "%" + formSearch.getDistrictName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getProvinceName())) {
            sql.append(" and LOWER(prv.name) like :provinceName ");
            params.put("provinceName",  "%" + formSearch.getProvinceName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getWardName())) {
            sql.append(" and LOWER(wa.name) like :wardName ");
            params.put("wardName", "%" + formSearch.getWardName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(formSearch.getRoleCode())) {
            sql.append(" and rl.roleCode = :roleCode");
            params.put("roleCode", formSearch.getRoleCode());
        }
        if(!count) {
            sql.append(" ORDER BY u.fullName ASC, u.username ASC ");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }

    private <T> TypedQuery<T> buildSearchTeacher(String teacherName, String roleCode, Class<T> clazz) {
        StringBuilder sql = new StringBuilder();
        sql.append("select new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName) "
                    + "from User u "
                    + " where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isNullorEmpty(teacherName)) {
            sql.append(" and LOWER(u.fullName) like :teacherName ");
            params.put("teacherName", "%" + teacherName.toLowerCase() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(roleCode)) {
            sql.append(" and u.roleCode = :roleCode");
            params.put("roleCode", roleCode);
        }
        sql.append(" ORDER BY u.fullName ASC");
        return super.createQuery(sql.toString(), params, clazz);
    }
}
