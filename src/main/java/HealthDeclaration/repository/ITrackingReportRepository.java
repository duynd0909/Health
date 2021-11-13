package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.TrackingReportDTO;
import HealthDeclaration.modal.entity.TrackingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ITrackingReportRepository extends JpaRepository<TrackingReport, Long> {
    /**
     *
     * @param username
     * @return
     */
    @Query("SELECT new HealthDeclaration.modal.dto.TrackingReportDTO(tr.createdTime,tr.username,tr.fullName,tr.verificationId" +
            ",tr.gender,tr.dateOfBirth,tr.phoneNumber,tr.healthInsuranceId,tr.email,pr.name,dt.name,w.name,tr.addressDetail" +
            " ,vc.name,tr.controlPlateNumber,tr.movingDate,pr1.name,dt1.name,w1.name,tr.addressDetailFrom" +
            ",pr2.name,dt2.name,w2.name,tr.addressDetailTo,tr.contactToPlace,tr.sicking,tr.closeToRiskingPeople,tr.closeToCountry,tr.closeToSicking) FROM TrackingReport tr " +
            " LEFT JOIN Province pr ON tr.provinceCode = pr.id  " +
            " LEFT JOIN District dt ON tr.districtCode = dt.id " +
            " LEFT JOIN Ward w ON tr.wardCode = w.id " +
            " LEFT JOIN Province pr1 ON tr.provinceCodeFrom = pr1.id  " +
            " LEFT JOIN District dt1 ON tr.districtCodeFrom = dt1.id " +
            " LEFT JOIN Ward w1 ON tr.wardCodeFrom = w1.id " +
            " LEFT JOIN Province pr2 ON tr.provinceCodeTo = pr2.id  " +
            " LEFT JOIN District dt2 ON tr.districtCodeTo = dt2.id " +
            " LEFT JOIN Ward w2 ON tr.wardCodeTo = w2.id " +
            "LEFT JOIN Vehicle vc ON vc.code = tr.vehicleType " +
            "WHERE tr.createdBy = :username AND tr.createdTime >= :dateTime " +
            " GROUP BY tr.id ")
    List<TrackingReportDTO> getTrackingReportByUsername(@Param("username") String username, @Param("dateTime")Date dateTime);
}
