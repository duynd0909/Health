package HealthDeclaration.service.serviceImpl;

import HealthDeclaration.common.base.service.BaseService;
import HealthDeclaration.common.utils.ObjectUtils;
import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.request.ClassAddForm;
import HealthDeclaration.modal.request.ClassUpdateForm;
import HealthDeclaration.repository.IUserRepository;
import HealthDeclaration.repository.IClassRepository;
import HealthDeclaration.repository.IClassRepositoryCustom;
import HealthDeclaration.service.IClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl extends BaseService implements IClassService {
    @Autowired
    IClassRepository classRepository;

    @Autowired
    IClassRepositoryCustom classRepositoryCustom;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ClassDto> getClasses() {
        List<Class> list = classRepository.findAll();
        List<ClassDto> classes = list.stream().map(s -> mapToClassDto(s)).collect(Collectors.toList());
        return classes;
    }

    @Override
    public List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize) {
        List<ClassDto> classDtoList = classRepositoryCustom.searchClass(classFormSearch, pageIndex, pageSize);
        for(int i = 0 ; i < classDtoList.size(); i++) {
            classDtoList.get(i).setIndex((long) ((pageSize * (pageIndex - 1)) + (i + 1)));
        }
        return classDtoList;
    }

    @Override
    public Long countSearchClass(ClassFormSearch classFormSearch) {
        return classRepositoryCustom.countSearchClass(classFormSearch);
    }

    @Override
    public Class addClass(ClassAddForm c) {
        if(!checkClassNameNotExist(c.getClassName())) {
            throw new IllegalArgumentException("Tên lớp học này đã tồn tại!");
        }
        List<Class> clazzList = classRepository.getByTeacherName(c.getTeacherUsername());
        if(!ObjectUtils.isNullorEmpty(clazzList)) {
            throw new IllegalArgumentException("Giáo viên này đã là chủ nhiệm của một lớp khác!");
        }
        Class clazz = new Class();
        String username = getLoggedInUsername();
        clazz.setCreatedBy(username);
        clazz.setCreatedTime(new Date());
        clazz.setModifiedBy(username);
        clazz.setModifiedTime(new Date());
        clazz.setName(c.getClassName());
        clazz.setTeacherUsername(c.getTeacherUsername());
        return classRepository.save(clazz);
    }

    @Override
    public Class updateClass(ClassUpdateForm c) {
        Class clazz = classRepository.getClassById(c.getId());
        if(!clazz.getName().equalsIgnoreCase(c.getClassName())) {
            if(!checkClassNameNotExist(c.getClassName())) {
                throw new IllegalArgumentException("Tên lớp học này đã tồn tại!");
            }
        }
        List<Class> clazzList = classRepository.getByTeacherName(c.getTeacherUsername());
        if(!ObjectUtils.isNullorEmpty(clazzList)) {
            throw new IllegalArgumentException("Giáo viên này đã là chủ nhiệm của một lớp khác!");
        }
        clazz.setModifiedBy(getLoggedInUsername());
        clazz.setModifiedTime(new Date());
        clazz.setName(c.getClassName());
        clazz.setTeacherUsername(c.getTeacherUsername());
        System.out.println(clazz);
        return classRepository.save(clazz);
    }

    @Override
    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    @Override
    public List<ClassDto> searchClassesByName(String className) {
        List<ClassDto> classDtoList = classRepository.searchClassesByName("%" + className + "%");
        return classDtoList;
    }

    @Override
    public List<Class> getByTeacherUser(String username) {
        return classRepository.getByTeacherName(username);
    }

    private ClassDto mapToClassDto(Class c) {
        ClassDto classDto = mapper.map(c, ClassDto.class);
        return classDto;
    }

    private Class mapToClass(ClassDto classDto) {
        Class c = mapper.map(classDto, Class.class);
        return c;
    }

    private boolean checkClassNameNotExist(String className) {
        Class clazz = classRepository.getByClassName(className);
        return ObjectUtils.isNullorEmpty(clazz);
    }

    @Override
    public Class getClasByName(String className) {
        return classRepository.getClasByName(className);
    }
}
