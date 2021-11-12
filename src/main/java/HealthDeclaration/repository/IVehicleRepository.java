package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.VehicleDTO;
import HealthDeclaration.modal.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long>, CrudRepository<Vehicle, Long> {

    @Query(value = "SELECT new HealthDeclaration.modal.dto.VehicleDTO(v.id, v.code, v.name) FROM Vehicle v " +
            "WHERE v.name like :vehicleName")
    List<VehicleDTO> getVehicleByName(@Param("vehicleName") String vehicleName);
}
