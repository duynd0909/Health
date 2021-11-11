package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.HealthFormDto;

import java.util.List;

public interface ITrackingReportRepositoryCustom {
    List<HealthFormDto> searchHealthForm(Long userId, int pageIndex, int pageSize);

    Long countSearchHealthFormReport(Long userId);
}
