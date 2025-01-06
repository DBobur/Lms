package uz.pdp.lms.modules.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.attendance.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
}
