package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.constants.RoleConstant;
import HealthDeclaration.modal.dto.RoleDTO;
import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.repository.IRoleRepository;
import HealthDeclaration.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role getByCode(String code) {
        return roleRepository.findByCode(code);
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.getAll();
    }

    @Override
    public List<RoleDTO> getRoleNotStudent() {
        return roleRepository.getRoleNotStudent(RoleConstant.ROLE_HOC_SINH);
    }
}
