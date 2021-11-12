package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.repository.IClassRepositoryCustom;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Transactional
@Log4j2
public class ClassRepositoryCustomImpl extends BaseRepository implements IClassRepositoryCustom {

    @Override
    public List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize) {
        TypedQuery<ClassDto> query = this.buildSearchClass(false , classFormSearch, ClassDto.class);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long countSearchClass(ClassFormSearch classFormSearch) {
        TypedQuery<Long> query = this.buildSearchClass(true , classFormSearch, Long.class);
        return query.getSingleResult();
    }

    private <T> TypedQuery<T> buildSearchClass(final boolean count, ClassFormSearch classFormSearch, Class<T> clazz) {
        StringBuilder sql = new StringBuilder();
        if (count) {
            sql.append("select count(cl.id) "
                + "from Class cl LEFT join User u " +
                "ON u.username = cl.teacherUsername "
                + " where 1=1 ");
        } else {
            sql.append("select new HealthDeclaration.modal.dto.ClassDto(cl.id, cl.name, u.id, u.username, u.fullName) "
                    + "from Class cl LEFT join User u " +
                    "ON u.username = cl.teacherUsername "
                    + " where 1=1 ");
        }
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isEmpty(classFormSearch.getClassName())) {
            sql.append(" and LOWER(cl.name) like :className ");
            params.put("className", "%" + classFormSearch.getClassName().toLowerCase() + "%");
        }
        if (!ObjectUtils.isEmpty(classFormSearch.getTeacherName())) {
            sql.append(" and ( LOWER(u.username) like :teacherName OR LOWER(u.fullName) like :teacherName)");
            params.put("teacherName", "%" + classFormSearch.getTeacherName().toLowerCase() + "%");
        }
        if (!count) {
            sql.append(" ORDER BY cl.name ASC");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }

}
