package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.DistrictAddForm;
import HealthDeclaration.modal.dto.DistrictDTO;
import HealthDeclaration.modal.entity.District;
import HealthDeclaration.repository.IDistrictRepository;
import HealthDeclaration.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IDistrictServiceImpl extends BaseService implements IDistrictService {

    @Autowired
    private IDistrictRepository districtRepository;

    @Override
    public void addListDistrict(List<DistrictAddForm> formList) {
        List<District> districtList = new ArrayList<>();
        if(!ObjectUtils.isNullorEmpty(formList)) {
            formList.forEach(element -> {
                District district = new District();
                district.setCreatedBy(getLoggedInUsername());
                district.setModifiedBy(getLoggedInUsername());
                district.setCreatedTime(new Date());
                district.setModifiedTime(new Date());
                district.setDeleted(false);
                district.setCode(element.getCode());
                district.setName(element.getName());
                district.setCodename(element.getCodename());
                district.setDivisionType(element.getDivision_type());
                district.setProvinceCode(element.getProvince_code());
                District districtNew = districtRepository.save(district);
                districtList.add(districtNew);
            });
        }
    }

    @Override
    public List<DistrictDTO> getDistrictByProvinceAndDistrictName(Long provinceCode, String districtName) {
        return districtRepository.getDistrictByProvinceAndDistrictName(provinceCode, "%" + districtName + "%");
    }
}
