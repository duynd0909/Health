package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.log.LogWapper;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.common.utils.StringUtils;
import HealthDeclaration.constants.RoleConstant;
import HealthDeclaration.form.UserAddForm;
import HealthDeclaration.form.UserFormSearch;
import HealthDeclaration.form.UserImportForm;
import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.entity.Role;
import HealthDeclaration.modal.entity.User;
import HealthDeclaration.modal.request.UserChangePassForm;
import HealthDeclaration.modal.request.UserUpdateForm;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.repository.IUserRepositoryCustom;
import HealthDeclaration.service.IClassService;
import HealthDeclaration.service.IRoleService;
import HealthDeclaration.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements IUserService {

    @Value("${default.password}")
    private String defaultPassword;

    @Autowired
    IUserRepository repository;

    @Autowired
    IUserRepositoryCustom userRepositoryCustom;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IClassService classService;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    private final LogWapper LOGGER = new LogWapper(this.getClass(),
            "USER SERVICE | " + this.getClass().getName());

    @Override
    public User update(UserUpdateForm updateForm) {
        User user = repository.getByUsername(updateForm.getUsername());
        user.setModifiedBy(getLoggedInUsername());
        user.setModifiedTime(new Date());
        if (ObjectUtils.isNullorEmpty(updateForm.getFullName())) {
            throw new IllegalArgumentException("Tên người dùng không được để trống!");
        }
        if (ObjectUtils.isNullorEmpty(updateForm.getGender())) {
            throw new IllegalArgumentException("Giới tính không được để trống!");
        }

        //Convert vietnames name to english name
        String newFullName = StringUtils.deAccent(updateForm.getFullName());
        String oldFullName = StringUtils.deAccent(user.getFullName());

        // Compare old name with new name if diffrebce --> set new account
        if (!oldFullName.equalsIgnoreCase(newFullName)) {
            user.setUsername(getNewAccountWithFullName(updateForm.getFullName()));
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getFullName())) {
            user.setFullName(updateForm.getFullName());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getDob())) {
            user.setDob(updateForm.getDob());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getGender())) {
            user.setGender(updateForm.getGender());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getPhoneNumber())) {
            user.setPhoneNumber(updateForm.getPhoneNumber());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getParentPhoneNumber())) {
            user.setParentPhoneNumber(updateForm.getParentPhoneNumber());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getProvinceCode())) {
            user.setProvinceCode(updateForm.getProvinceCode());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getDistrictCode())) {
            user.setDistrictCode(updateForm.getDistrictCode());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getWardCode())) {
            user.setWardCode(updateForm.getWardCode());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getRoleCode())) {
            user.setRoleCode(updateForm.getRoleCode());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getAddressDetail())) {
            user.setAddressDetail(updateForm.getAddressDetail());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getHealthInsuranceId())) {
            user.setHealthInsuranceId(updateForm.getHealthInsuranceId());
        }
        if (!ObjectUtils.isNullorEmpty(updateForm.getGmail())) {
            if (updateForm.getGmail().contains("@gmail.com")) {
                user.setGmail(updateForm.getGmail());
            } else {
                throw new IllegalArgumentException("Email phải gồm đuôi \"@gmail.com\"");
            }
        }
        return repository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = repository.getByUsername(username);
        if (!ObjectUtils.isNullorEmpty(user)) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với tài khoản " + username + "!");
        }
        user.setModifiedBy(getLoggedInUsername());
        user.setModifiedTime(new Date());
        repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDto> searchTeacherByName(String teacherName, int pPageIndex, int pageSize) {
        Role teacherRole = roleService.getByCode(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM);
        if (!ObjectUtils.isNullorEmpty(teacherRole)) {
            return userRepositoryCustom.searchTeacherByName(teacherName,
                    teacherRole.getRoleCode(), pPageIndex, pageSize);
        }
        return null;
    }

    @Override
    public boolean changePassword(UserChangePassForm form) {
        User user = repository.getByUsername(form.getUsername());
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(form.getOldPassword())) {
            return false;
        }
        // newpass # oldpass
        if (user.getPassword().equals(form.getNewPassword())) {
            return false;
        }
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            return false;
        }
        user.setPassword(form.getNewPassword());
        repository.save(user);
        return true;
    }

    @Override
    public List<UserDto> searchUserToManagement(UserFormSearch formSearch, int pageIndex, int pageSize) {
        String roleCodeOFUser = getLoggedInUserRoles();
        List<UserDto> resultList = new ArrayList<>();
        if (ObjectUtils.isNullorEmpty(roleCodeOFUser)) {
            throw new IllegalArgumentException("You don't have permission to see that data!");
        }

        if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
            String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
            if (gender.equalsIgnoreCase("nam")) {
                formSearch.setGenderSearch(true);
            } else if (gender.equalsIgnoreCase("nu")) {
                formSearch.setGenderSearch(false);
            } else {
                return null;
            }
        }
        resultList = userRepositoryCustom.searchStudentToManagement(formSearch, pageIndex, pageSize);

        if (!ObjectUtils.isNullorEmpty(resultList)) {
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).setIndex(pageSize * (pageIndex - 1) + i + 1);
            }
            ;
        }
        return resultList;
    }

    @Override
    public Long countSearchUserToManagement(UserFormSearch formSearch) {
        String roleCodeOFUser = getLoggedInUserRoles();
        if (ObjectUtils.isNullorEmpty(roleCodeOFUser)) {
            throw new IllegalArgumentException("You don't have permission to see that data!");
        }
        if (roleCodeOFUser.equalsIgnoreCase(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM)) {
            List<Class> clazzList = classService.getByTeacherUser(getLoggedInUsername());

            if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
                String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
                if (gender.equalsIgnoreCase("nam")) {
                    formSearch.setGenderSearch(true);
                } else if (gender.equalsIgnoreCase("nu")) {
                    formSearch.setGenderSearch(false);
                } else {
                    return null;
                }
            }
            if (!ObjectUtils.isNullorEmpty(clazzList)) {
                formSearch.setClassID(clazzList.get(clazzList.size() - 1).getId());
            } else {
                return null;
            }
        } else if (roleCodeOFUser.equalsIgnoreCase(RoleConstant.ROLE_HIEU_TRUONG)) {
            if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
                String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
                if (gender.equalsIgnoreCase("nam")) {
                    formSearch.setGenderSearch(true);
                } else if (gender.equalsIgnoreCase("nu")) {
                    formSearch.setGenderSearch(false);
                } else {
                    return null;
                }
            }
        } else {
            throw new IllegalArgumentException("You don't have permission to see that data!");
        }

        return userRepositoryCustom.countSearchUserToManagement(formSearch);
    }

    @Override
    public User addNewStudent(UserAddForm userAddForm) {
        User user = new User();
        user.setCreatedBy(getLoggedInUsername());
        user.setCreatedTime(new Date());
        user.setModifiedBy(getLoggedInUsername());
        user.setModifiedTime(new Date());
        if (!ObjectUtils.isNullorEmpty(userAddForm.getFullName())) {
            user.setFullName(userAddForm.getFullName());
        } else {
            throw new IllegalArgumentException("Full name can not be blank!");
        }
        user.setDob(userAddForm.getDob());
        user.setGender(userAddForm.getGender());
        user.setPhoneNumber(userAddForm.getPhoneNumber());
        user.setParentPhoneNumber(userAddForm.getParentPhoneNumber());
        user.setProvinceCode(userAddForm.getProvinceCode());
        user.setDistrictCode(userAddForm.getDistrictCode());
        user.setWardCode(userAddForm.getWardCode());
        user.setAddressDetail(userAddForm.getAddressDetail());
        Role role = roleService.getByCode(RoleConstant.ROLE_HOC_SINH);
        if (!ObjectUtils.isNullorEmpty(role)) {
            user.setRoleCode(role.getRoleCode());
        }
        String username = getNewAccountWithFullName(userAddForm.getFullName());
        if (!ObjectUtils.isNullorEmpty(username)) {
            user.setUsername(username);
        } else {
            throw new IllegalArgumentException("Fail in create account!");
        }
        user.setPassword(defaultPassword);
        user.setClassID(userAddForm.getClassID());
        return repository.save(user);
    }

    @Override
    public List<UserDto> searchTeacherFreeByName(String teacherName) {
        return repository.getTeacherFreeByName(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM,
                RoleConstant.ROLE_GIAO_VIEN_BO_MON, "%" + teacherName + "%");
    }

    @Override
    public List<UserDto> searchTeacherToManagement(UserFormSearch formSearch, int pageIndex, int pageSize) {
        String roleCodeOFUser = getLoggedInUserRoles();
        List<UserDto> resultList = new ArrayList<>();
        if (ObjectUtils.isNullorEmpty(roleCodeOFUser)
                || (!roleCodeOFUser.equalsIgnoreCase(RoleConstant.ROLE_HIEU_TRUONG)
                && !roleCodeOFUser.equalsIgnoreCase(RoleConstant.ROLE_HIEU_PHO))) {
            throw new IllegalArgumentException("You don't have permission to see that data!");
        }

        if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
            String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
            if (gender.equalsIgnoreCase("nam")) {
                formSearch.setGenderSearch(true);
            } else if (gender.equalsIgnoreCase("nu")) {
                formSearch.setGenderSearch(false);
            } else {
                return null;
            }
        }
        resultList = userRepositoryCustom.searchTeacherToManagement(formSearch, pageIndex, pageSize, RoleConstant.ROLE_HOC_SINH);

        if (!ObjectUtils.isNullorEmpty(resultList)) {
            for (int i = 0; i < resultList.size(); i++) {
                resultList.get(i).setIndex(pageSize * (pageIndex - 1) + i + 1);
            }
            ;
        }
        return resultList;
    }

    @Override
    public Long countSearchTeacherToManagement(UserFormSearch formSearch) {

        if (!ObjectUtils.isNullorEmpty(formSearch.getGender())) {
            String gender = StringUtils.removeAccent(formSearch.getGender()).toLowerCase();
            if (gender.equalsIgnoreCase("nam")) {
                formSearch.setGenderSearch(true);
            } else if (gender.equalsIgnoreCase("nu")) {
                formSearch.setGenderSearch(false);
            } else {
                return null;
            }
        }
        return userRepositoryCustom.countSearchTeacherToManagement(formSearch, RoleConstant.ROLE_HOC_SINH);
    }

    @Override
    public User addNewTeacher(UserAddForm userAddForm) {
        User user = new User();
        user.setCreatedBy(getLoggedInUsername());
        user.setCreatedTime(new Date());
        user.setModifiedBy(getLoggedInUsername());
        user.setModifiedTime(new Date());
        if (!ObjectUtils.isNullorEmpty(userAddForm.getFullName())) {
            user.setFullName(userAddForm.getFullName());
        } else {
            throw new IllegalArgumentException("Full name can not be blank!");
        }
        user.setDob(userAddForm.getDob());
        user.setGender(userAddForm.getGender());
        user.setPhoneNumber(userAddForm.getPhoneNumber());
        user.setParentPhoneNumber(userAddForm.getParentPhoneNumber());
        user.setProvinceCode(userAddForm.getProvinceCode());
        user.setDistrictCode(userAddForm.getDistrictCode());
        user.setWardCode(userAddForm.getWardCode());
        user.setAddressDetail(userAddForm.getAddressDetail());
        user.setRoleCode(userAddForm.getRoleCode());
        String username = getNewAccountWithFullName(userAddForm.getFullName());
        if (!ObjectUtils.isNullorEmpty(username)) {
            user.setUsername(username);
        } else {
            throw new IllegalArgumentException("Fail in create account!");
        }
        user.setPassword(defaultPassword);
        user.setClassID(userAddForm.getClassID());
        return repository.save(user);
    }

    @Override
    public Boolean getAllowedViewReport(String username) {
        return repository.getAllowedViewReport(username);
    }

    @Override
    public String resetPasswordByUsername(String username) {
        String role = getLoggedInUserRoles();
        if (role.equalsIgnoreCase(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM)
                || role.equalsIgnoreCase(RoleConstant.ROLE_HIEU_TRUONG)
                || role.equalsIgnoreCase(RoleConstant.ROLE_HIEU_PHO)) {

            User user = repository.getByUsername(username);
            if (!ObjectUtils.isNullorEmpty(user)) {
                user.setModifiedTime(new Date());
                user.setModifiedBy(getLoggedInUsername());
                user.setPassword(defaultPassword);
                repository.save(user);
                return defaultPassword;
            } else {
                LOGGER.error("Không thể đặt lại mật khẩu cho tài khoản " + username);
            }
        }
        return null;
    }

    @Override
    public String changePasswordByUsername(UserChangePassForm form) {
        String role = getLoggedInUserRoles();
        if (ObjectUtils.isNullorEmpty(form.getUsername())
                || ObjectUtils.isNullorEmpty(form.getNewPassword())
                || ObjectUtils.isNullorEmpty(form.getConfirmPassword())) {
            LOGGER.error("Thông tin tài khoản mật khẩu chưa hợp lệ!");
        }

        if (ObjectUtils.isNullorEmpty(form.getOldPassword())
                && !role.equalsIgnoreCase(RoleConstant.ROLE_GIAO_VIEN_CHU_NHIEM)
                && !role.equalsIgnoreCase(RoleConstant.ROLE_HIEU_TRUONG)
                && !role.equalsIgnoreCase(RoleConstant.ROLE_HIEU_PHO)) {
            LOGGER.error("Không thể đặt lại mật khẩu cho tài khoản này!");
        }
        User user = repository.getByUsername(form.getUsername());
        if (!ObjectUtils.isNullorEmpty(user)) {
            user.setModifiedTime(new Date());
            user.setModifiedBy(getLoggedInUsername());
            user.setPassword(form.getNewPassword());
            repository.save(user);
            return form.getNewPassword();
        } else {
            LOGGER.error("Không thể đổi mật khẩu cho tài khoản " + form.getUsername());
        }
        return null;
    }

    @Override
    public String changeYourPassword(UserChangePassForm form) {
        String username = getLoggedInUsername();
        if (ObjectUtils.isNullorEmpty(username)
                || ObjectUtils.isNullorEmpty(form.getOldPassword())
                || ObjectUtils.isNullorEmpty(form.getNewPassword())
                || ObjectUtils.isNullorEmpty(form.getConfirmPassword())) {
            LOGGER.error("Thông tin tài khoản mật khẩu chưa hợp lệ!");
        }

        User user = repository.getByUsernameAndPassword(form.getUsername(), form.getOldPassword());
        if (!ObjectUtils.isNullorEmpty(user)) {
            user.setModifiedTime(new Date());
            user.setModifiedBy(getLoggedInUsername());
            user.setPassword(form.getNewPassword());
            repository.save(user);
            return form.getNewPassword();
        } else {
            LOGGER.error("Mật khẩu không hợp lệ");
        }
        return "Mật khẩu không hợp lệ";
    }

    private String getNewAccountWithFullName(String fullName) {
        String account = null;
        fullName = StringUtils.convertVietnameseToEnglish(fullName);
        List<String> list = Arrays.asList(fullName.split(" "));
        int sizeName = list.size();
        if (sizeName > 1) {
            account = list.get(sizeName - 1);
            for (int i = 0; i < sizeName - 1; i++) {
                account = account + list.get(i).substring(0, 1);
            }
        }
        account = account.toLowerCase();
        List<String> accList = repository.getLastAccountByAccount(account + "%");
        if (ObjectUtils.isNullorEmpty(accList)) {
            return account;
        }
        for (int i = accList.size() - 1; i >= 0; i--) {
            String accountInList = accList.get(i);
            accountInList = accountInList.replace(account, "");
            if (ObjectUtils.isNullorEmpty(accountInList)) {
                account = account + "1";
            } else if (StringUtils.isNumeric(accountInList)) {
                account = account + (Integer.valueOf(accountInList) + 1);
                break;
            }
        }
        return account;
    }

    @Override
    public boolean addNewListStudent(List<UserImportForm> list) {
        try {
            List<User> listUser = new ArrayList<>();
            for (UserImportForm userImportForm : list
            ) {
                User user = new User();
                user.setCreatedBy(getLoggedInUsername());
                user.setCreatedTime(new Date());
                user.setModifiedBy(getLoggedInUsername());
                user.setModifiedTime(new Date());
                if (!ObjectUtils.isNullorEmpty(userImportForm.getFullName())) {
                    user.setFullName(userImportForm.getFullName());
                } else {
                    throw new IllegalArgumentException("Full name can not be blank!");
                }
                if (!ObjectUtils.isNullorEmpty(userImportForm.getGender())) {
                    if (userImportForm.getGender().equalsIgnoreCase("Nam")) {
                        user.setGender(true);
                    } else {
                        user.setGender(false);
                    }
                } else {
                    user.setGender(false);
                }
                Role role = roleService.getByCode(RoleConstant.ROLE_HOC_SINH);
                if (!ObjectUtils.isNullorEmpty(role)) {
                    user.setRoleCode(role.getRoleCode());
                }
                String username = getNewAccountWithFullName(userImportForm.getFullName());
                if (!ObjectUtils.isNullorEmpty(username)) {
                    user.setUsername(username);
                } else {
                    throw new IllegalArgumentException("Fail in create account!");
                }
                user.setPassword(defaultPassword);
                Class clazz = classService.getClasByName(userImportForm.getClassName());
                user.setClassID(clazz.getId());
                listUser.add(user);
            }
            repository.saveAll(listUser);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
