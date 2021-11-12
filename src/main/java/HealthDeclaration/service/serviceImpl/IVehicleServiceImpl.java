package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.modal.dto.VehicleDTO;
import HealthDeclaration.repository.IVehicleRepository;
import HealthDeclaration.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IVehicleServiceImpl extends BaseService implements IVehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Override
    public List<VehicleDTO> getVehicleByName(String vehicleName) {
        return vehicleRepository.getVehicleByName("%" + vehicleName + "%");
    }
}
