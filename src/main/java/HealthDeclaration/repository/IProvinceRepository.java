package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.ProvinceDTO;
import HealthDeclaration.modal.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, Long>, CrudRepository<Province, Long> {

    @Query(value = "select new HealthDeclaration.modal.dto.ProvinceDTO(p.id, p.code, p.name) from Province p")
    List<ProvinceDTO> getAll();

    @Query(value = "select new HealthDeclaration.modal.dto.ProvinceDTO(p.id, p.code, p.name) from Province p WHERE p.name like :provinceName ")
    List<ProvinceDTO> getByName( @Param("provinceName") String provinceName);
}
