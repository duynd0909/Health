package HealthDeclaration.service;

import HealthDeclaration.form.HealthReportAddForm;
import HealthDeclaration.modal.dto.HealthFormDto;
import HealthDeclaration.modal.entity.TrackingReport;

import java.util.List;

public interface ITrackingReportService {
    TrackingReport add(HealthReportAddForm formReportAdd);

    void delete(Long id);

    List<HealthFormDto> searchHealthFormReport(Long userId, int pageIndex, int pageSize);

    Long countSearchHealthFormReport(Long userId);

    TrackingReport getById(Long id);
}
