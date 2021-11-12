package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.WardDTO;
import HealthDeclaration.modal.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWardRepository extends JpaRepository<Ward, Long>, CrudRepository<Ward, Long> {

    @Query(value = "SELECT new HealthDeclaration.modal.dto.WardDTO(w.id, w.code, w.name, w.districtCode) FROM Ward w " +
            " WHERE w.name like :wardName and w.districtCode = :districtCode ")
    List<WardDTO> getWardByDistrictCodeAndWardName( @Param("districtCode") Long districtCode, @Param("wardName") String wardName);
}
