package uz.pdp.lms.modules.schedule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uz.pdp.lms.core.common.BaseEntity;
import uz.pdp.lms.modules.user.entity.User;

import java.time.LocalDate;

@Entity
public class Attendance extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;
    private boolean isPresent;

    // Constructors, Getters, Setters
}

