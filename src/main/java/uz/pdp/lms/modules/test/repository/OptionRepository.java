package uz.pdp.lms.modules.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.test.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
