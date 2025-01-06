package uz.pdp.lms.modules.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.test.entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
