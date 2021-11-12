package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.WardAddForm;
import HealthDeclaration.modal.dto.WardDTO;
import HealthDeclaration.modal.entity.District;
import HealthDeclaration.modal.entity.Ward;
import HealthDeclaration.repository.IWardRepository;
import HealthDeclaration.service.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IWardServiceImpl extends BaseService implements IWardService {

    @Autowired
    private IWardRepository wardRepository;

    @Override
    public void addListward(List<WardAddForm> formList) {
        List<Ward> wardsList = new ArrayList<>();
        if(!ObjectUtils.isNullorEmpty(formList)) {
            formList.forEach(element -> {
                Ward ward = new Ward();
                ward.setCreatedBy(getLoggedInUsername());
                ward.setModifiedBy(getLoggedInUsername());
                ward.setCreatedTime(new Date());
                ward.setModifiedTime(new Date());
                ward.setDeleted(false);
                ward.setName(element.getName());
                ward.setCode(element.getCode());
                ward.setDivisionType(element.getDivision_type());
                ward.setCodename(element.getCodename());
                ward.setDistrictCode(element.getDistrict_code());
                wardsList.add(ward);
                wardRepository.save(ward);
            });
        }
    }

    @Override
    public List<WardDTO> getWardByDistrictCodeAndWardName(Long districtCode, String wardName) {
        return wardRepository.getWardByDistrictCodeAndWardName(districtCode, "%" + wardName + "%");
    }
}
