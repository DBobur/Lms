package uz.pdp.lms.modules.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.test.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
