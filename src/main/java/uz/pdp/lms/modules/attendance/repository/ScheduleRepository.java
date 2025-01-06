package uz.pdp.lms.modules.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.attendance.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
