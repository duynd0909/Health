package HealthDeclaration.service;

import HealthDeclaration.modal.dto.ProvinceDTO;
import HealthDeclaration.modal.request.ProvinceAddRequest;

import java.util.List;

public interface IProvinceService {
    List<ProvinceDTO> getAll();

    void add(List<ProvinceAddRequest> list);

    List<ProvinceDTO> getByName(String provinceName);
}
