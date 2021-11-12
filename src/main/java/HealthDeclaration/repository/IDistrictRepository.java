package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.DistrictDTO;
import HealthDeclaration.modal.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDistrictRepository extends JpaRepository<District, Long>, CrudRepository<District, Long> {

    @Query(value = "select new HealthDeclaration.modal.dto.DistrictDTO(d.id, d.code, d.name, d.provinceCode) FROM District d " +
            " WHERE d.provinceCode = :provinceCode and d.name like :districtName ")
    List<DistrictDTO> getDistrictByProvinceAndDistrictName(@Param("provinceCode") Long provinceCode, @Param("districtName") String districtName);
}
