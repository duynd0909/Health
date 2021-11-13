package HealthDeclaration.repository;

import HealthDeclaration.modal.dto.UserDto;
import HealthDeclaration.modal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    @Query("select u from User u WHERE u.username = :username")
    User getByUsername(@Param("username") String username);

    @Query("select r.roleCode from User u join Role r ON r.roleCode = u.roleCode WHERE u.username = :username")
    String getUserRoleByUsername(@Param("username") String username);

    @Query(value = "SELECT u.username FROM User u WHERE u.username LIKE :account ORDER BY u.username ASC")
    List<String> getLastAccountByAccount(@Param("account") String account);

    @Query(value = "SELECT new HealthDeclaration.modal.dto.UserDto(u.id, u.username, u.fullName) FROM User u " +
            " WHERE (u.roleCode = :roleGVCN OR u.roleCode = :roleGVBM) AND u.fullName like :teacherName " +
            " AND u.username NOT IN ( " +
            " SELECT cl.teacherUsername " +
            " FROM Class cl " +
            " WHERE cl.teacherUsername IS NOT NULL " +
            " AND cl.teacherUsername != '' " +
            ") ")
    List<UserDto> getTeacherFreeByName(@Param("roleGVCN") String roleGVCN,
                                       @Param("roleGVBM") String roleGVBM,
                                       @Param("teacherName") String teacherName);

    @Query(value = "SELECT u FROM User u WHERE u.classID = :classId")
    List<User> getAllUserByClassId(@Param("classId") Long classId);

    @Query(value = "SELECT u.allowViewReport FROM User u WHERE u.username = :username")
    Boolean getAllowedViewReport(@Param("username") String username);

    @Query(value = "SELECT u FROM User u WHERE u.username = :username and u.password = :password ")
    User getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
