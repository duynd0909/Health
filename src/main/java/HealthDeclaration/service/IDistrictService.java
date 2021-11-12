package HealthDeclaration.service;

import HealthDeclaration.form.DistrictAddForm;
import HealthDeclaration.modal.dto.DistrictDTO;

import java.util.List;

public interface IDistrictService {

    void addListDistrict(List<DistrictAddForm> formList);

    List<DistrictDTO> getDistrictByProvinceAndDistrictName(Long provinceCode, String districtName);
}
