package HealthDeclaration.service;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import HealthDeclaration.modal.request.ClassAddForm;
import HealthDeclaration.modal.request.ClassUpdateForm;

import java.util.List;

public interface IClassService {
    List<ClassDto> getClasses();

    List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize);

    Long countSearchClass(ClassFormSearch classFormSearch);

    Class addClass(ClassAddForm c);

    Class updateClass(ClassUpdateForm c);

    void deleteClass(Long id);

    List<ClassDto> searchClassesByName(String className);

    List<Class> getByTeacherUser(String username);
}
