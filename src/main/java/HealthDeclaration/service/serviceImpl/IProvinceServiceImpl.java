package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.modal.dto.ProvinceDTO;
import HealthDeclaration.modal.entity.Province;
import HealthDeclaration.modal.request.ProvinceAddRequest;
import HealthDeclaration.repository.IProvinceRepository;
import HealthDeclaration.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IProvinceServiceImpl extends BaseService implements IProvinceService {
    @Autowired
    private IProvinceRepository repository;

    @Override
    public List<ProvinceDTO> getAll() {
        return repository.getAll();
    }

    @Override
    public void add(List<ProvinceAddRequest> list) {
        for (int i = 0; i < list.size(); i++) {
            Province p = new Province();
            p.setCreatedBy(getLoggedInUsername());
            p.setCreatedTime(new Date());
            p.setModifiedBy(getLoggedInUsername());
            p.setModifiedTime(new Date());
            p.setCode(list.get(i).getCode());
            p.setName(list.get(i).getName());
            p.setDivisionType(list.get(i).getDivision_type());
            p.setCodeName(list.get(i).getCodename());
            p.setPhoneCode(list.get(i).getPhone_code());
            repository.save(p);
        }
    }

    @Override
    public List<ProvinceDTO> getByName(String provinceName) {
        return repository.getByName("%" + provinceName + "%");
    }
}
