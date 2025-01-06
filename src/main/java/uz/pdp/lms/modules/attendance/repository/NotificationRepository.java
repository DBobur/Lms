package uz.pdp.lms.modules.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lms.modules.attendance.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
