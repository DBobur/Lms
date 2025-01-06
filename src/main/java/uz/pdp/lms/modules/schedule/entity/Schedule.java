package uz.pdp.lms.modules.schedule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uz.pdp.lms.core.common.BaseEntity;
import uz.pdp.lms.core.common.Group;
import uz.pdp.lms.modules.user.entity.User;

import java.time.LocalDateTime;

@Entity
public class Schedule extends BaseEntity {
    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")  // Foreign key
    private Group group;
}
