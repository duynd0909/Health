package HealthDeclaration.repository;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;

import java.util.List;

public interface IClassRepositoryCustom {

    List<ClassDto> searchClass(ClassFormSearch classFormSearch, int pageIndex, int pageSize);

    Long countSearchClass(ClassFormSearch classFormSearch);
}
