package uz.pdp.lms.modules.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
}
