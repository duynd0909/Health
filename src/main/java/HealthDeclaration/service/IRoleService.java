package HealthDeclaration.service;

import HealthDeclaration.modal.dto.RoleDTO;
import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.modal.entity.User;

import java.util.List;

public interface IRoleService {

    Role getByCode(String code);

    List<RoleDTO> getAll();

    List<RoleDTO> getRoleNotStudent();
}
