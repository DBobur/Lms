package uz.pdp.lms.modules.attendance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uz.pdp.lms.core.common.BaseEntity;
import uz.pdp.lms.modules.user.entity.User;

import java.time.LocalDateTime;

@Entity
public class Notification extends BaseEntity {
    private String message;
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime timestamp;

    // Constructors, Getters, Setters
}

