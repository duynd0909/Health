package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.RoleDTO;
import HealthDeclaration.modal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select r FROM Role r where r.roleCode = :roleCode ")
    Role findByCode(@Param("roleCode") String code);

    @Query(value = "select new HealthDeclaration.modal.dto.RoleDTO(r.id, r.roleCode, r.roleName) FROM Role r")
    List<RoleDTO> getAll();

    @Query(value = "select new HealthDeclaration.modal.dto.RoleDTO(r.id, r.roleCode, r.roleName) FROM Role r " +
            "where r.roleCode <> :roleHocSinh ")
    List<RoleDTO> getRoleNotStudent(@Param("roleHocSinh") String roleHocSinh);
}
