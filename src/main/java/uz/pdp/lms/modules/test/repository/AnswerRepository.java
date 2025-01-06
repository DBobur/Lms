package uz.pdp.lms.modules.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.test.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
