package uz.pdp.lms.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.lms.modules.user.entity.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    List<Permission> findByNameIn(List<String> names);
}
