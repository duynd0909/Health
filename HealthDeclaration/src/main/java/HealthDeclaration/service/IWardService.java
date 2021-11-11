package HealthDeclaration.service;

import HealthDeclaration.form.WardAddForm;
import HealthDeclaration.modal.dto.WardDTO;

import java.util.List;

public interface IWardService {

    void addListward(List<WardAddForm> formList);

    List<WardDTO> getWardByDistrictCodeAndWardName(Long districtCode, String wardName);
}
