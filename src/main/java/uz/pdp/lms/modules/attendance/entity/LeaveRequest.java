package uz.pdp.lms.modules.attendance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uz.pdp.lms.core.common.BaseEntity;
import uz.pdp.lms.modules.user.entity.User;

import java.time.LocalDate;

@Entity
public class LeaveRequest extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;
    private String status;
}

