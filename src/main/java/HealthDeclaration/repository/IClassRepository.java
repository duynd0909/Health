package HealthDeclaration.repository;

import HealthDeclaration.form.ClassFormSearch;
import HealthDeclaration.modal.dto.ClassDto;
import HealthDeclaration.modal.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class, Long>, CrudRepository<Class, Long> {

    @Query(value = "Select cl FROM Class cl where cl.name = :className")
    Class getByClassName(@Param("className") String className);

    @Query(value = "Select new HealthDeclaration.modal.dto.ClassDto(cl.id, cl.name,u.id,u.username,u.fullName,u.allowViewReport) " +
            "FROM Class cl JOIN User u ON cl.teacherUsername = u.username where cl.name like :className")
    List<ClassDto> searchClassesByName(@Param("className") String className);

    @Query(value = "Select cl FROM Class cl where cl.teacherUsername = :teacher")
    List<Class> getByTeacherName(@Param("teacher") String username);

    @Query(value = "Select cl FROM Class cl where cl.id = :id")
    Class getClassById(@Param("id") Long id);
}
