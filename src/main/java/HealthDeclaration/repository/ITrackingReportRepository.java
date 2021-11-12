package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.TrackingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrackingReportRepository extends JpaRepository<TrackingReport, Long> {
}
