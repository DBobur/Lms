package uz.pro.usm.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String superAdminUsername);

    Optional<User> findByEmailOrNumber(String identifier, String identifier1);

    Optional<User> findByIdAndDeleted(Long id, boolean b);
}
