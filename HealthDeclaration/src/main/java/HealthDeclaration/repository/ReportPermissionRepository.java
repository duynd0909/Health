package HealthDeclaration.repository;

import HealthDeclaration.modal.entity.ReportPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPermissionRepository  extends JpaRepository<ReportPermission,Long > {

    /**
     *
     * @param username
     * @return
     */
    @Query("SELECT rp FROM ReportPermission rp WHERE rp.username = :username")
    List<ReportPermission> findAllowedClassByUsername(@Param("username") String username);

    /**
     *
     * @param username
     * @return
     */
    @Query("SELECT rp.classId FROM ReportPermission rp WHERE rp.username = :username")
    List<Long> findAllowedClassIdByUsername(@Param("username") String username);
}
