package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.HealthReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import HealthDeclaration.modal.entity.HealthReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthReportRepository extends JpaRepository<HealthReport, Long> {
    /**
     * @param username
     * @return
     */
    @Query(" SELECT new HealthDeclaration.modal.dto.HealthReportDTO(hr.id,hr.createdBy,hr.createdTime,hr.modifiedBy,hr.modifiedTime," +
            "hr.username,hr.fullName,hr.verificationId,hr.gender,hr.dateOfBirth,hr.phoneNumber,hr.healthInsuranceId," +
            "hr.email,hr.provinceCode,hr.districtCode,hr.wardCode,hr.addressDetail,hr.closeToSicking,hr.closeToCountry,hr.closeToRiskingPeople," +
            "hr.contactToPlace,hr.sicking,prv.name,dis.name,wa.name)  FROM HealthReport hr  "
            + " LEFT JOIN Province prv ON prv.code = hr.provinceCode "
            + " LEFT JOIN District dis ON dis.code = hr.districtCode "
            + " LEFT JOIN Ward wa ON wa.code = hr.wardCode  "
            +"  WHERE hr.username = :username ")
    List<HealthReportDTO> getReportsByUsername(@Param("username") String username);
}
