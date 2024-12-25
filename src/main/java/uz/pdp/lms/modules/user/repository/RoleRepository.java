package uz.pdp.lms.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.lms.modules.user.entity.Role;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String user);

    @Query("SELECT r FROM Role r WHERE r.id IN :ids")
    Set<Role> findAllByIdIn(@Param("ids") Set<Long> ids);

}
