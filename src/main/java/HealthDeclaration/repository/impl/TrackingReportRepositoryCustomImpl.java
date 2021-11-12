package HealthDeclaration.repository.impl;

import HealthDeclaration.common.base.repository.BaseRepository;
import HealthDeclaration.modal.dto.HealthFormDto;
import HealthDeclaration.repository.ITrackingReportRepositoryCustom;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Log4j2
public class TrackingReportRepositoryCustomImpl extends BaseRepository implements ITrackingReportRepositoryCustom {
    @Override
    public List<HealthFormDto> searchHealthForm(Long userId, int pageIndex, int pageSize) {
        TypedQuery<HealthFormDto> query = this.buildSearchHealthFormReport(false, userId, HealthFormDto.class);
        query.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Long countSearchHealthFormReport(Long userId) {
        TypedQuery<Long> query = this.buildSearchHealthFormReport(true , userId, Long.class);
        return query.getSingleResult();
    }

    private <T> TypedQuery<T> buildSearchHealthFormReport(final boolean count, Long userId, Class<T> clazz) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (count) {
            sql.append("select count(hfr.id) from TrackingReport hfr where  1 = 1 ");
        } else {
            sql.append("select new HealthDeclaration.modal.dto.HealthFormDto(hfr.id, hfr.createdTime, hfr.userId, hfr.reportType, hfr.status) "
                    + "from TrackingReport hfr "
                    + "where 1=1 ");
        }
        if (userId != null ) {
            sql.append("and hfr.userId = :userId ");
            params.put("userId", userId);
        }
        Date date = Date.from(LocalDate.now().minusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant());
        //sql.append("and hfr.ngayKhaiBao > '2021-10-13' ");
        sql.append("and hfr.createdTime > :dateNow ");
        params.put("dateNow", date);
        if (!count) {
            sql.append(" ORDER BY hfr.createdTime ASC");
        }
        return super.createQuery(sql.toString(), params, clazz);
    }
}
