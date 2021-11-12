package HealthDeclaration.service;

import HealthDeclaration.modal.dto.VehicleDTO;

import java.util.List;

public interface IVehicleService {

    List<VehicleDTO> getVehicleByName(String vehicleName);
}
