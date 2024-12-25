package uz.pdp.lms.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lms.modules.user.entity.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    Optional<Permission> findByName(String name);

    @Query("SELECT p FROM Permission p WHERE p.id IN :ids")
    Set<Permission> findAllByIdIn(@Param("ids") Set<Long> ids);

}
