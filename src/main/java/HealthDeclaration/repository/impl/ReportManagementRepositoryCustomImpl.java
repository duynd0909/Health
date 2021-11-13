package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.ReportManagementSearchForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.repository.ReportManagementRepositoryCustom;
import HealthDeclaration.repository.ReportPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportManagementRepositoryCustomImpl extends BaseRepository implements ReportManagementRepositoryCustom {


    @Autowired
    ReportPermissionRepository reportPermissionRepository;

    @Override
    public List<UserDto> searchStudent(UserFormSearch form, int pageIndex, int pageSize) {
        TypedQuery<UserDto> query = this.buildSearchReportManagement(false, form, UserDto.class);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        List<UserDto> listResult = query.getResultList();
        int i=1;
        for (UserDto dto:
                listResult ) {
            dto.setIndex(i);
            i++;
        }
        return listResult;
    }

    @Override
    public Long countStudent(UserFormSearch form) {
        TypedQuery<Long> query = this.buildSearchReportManagement(true, form, Long.class);
        return query.getSingleResult();
    }

    private <T> TypedQuery<T> buildSearchReportManagement(final boolean count, UserFormSearch formSearch, Class<T> clazz) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (count) {
            sql.append("SELECT COUNT (u.id) ");
        } else {
            sql.append(" SELECT  new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName, u.dob, u.gender," +
                    " u.phoneNumber, u.parentPhoneNumber, u.provinceCode, prv.name as provinceName, u.districtCode, " +
                    " dis.name as districtName, u.wardCode, wa.name as wardName, u.addressDetail, u.roleCode, " +
                    " rl.roleName, u.classID, cl.name, cl.teacherUsername ,u.healthInsuranceId,u.gmail,u.allowViewReport) "  );
        }
        sql.append( " FROM User u "
                + " LEFT JOIN Province prv ON prv.code = u.provinceCode "
                + " LEFT JOIN District dis ON dis.code = u.districtCode "
                + " JOIN Class cl ON cl.id = u.classID "
                + " LEFT JOIN Ward wa ON wa.code = u.wardCode "
                + " LEFT JOIN Role rl ON rl.roleCode = u.roleCode ");
        sql.append(" WHERE  1=1 ");
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
        if (!count) {
            sql.append(" GROUP BY u.id ");
            sql.append(" ORDER BY u.createdTime DESC");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }
}
