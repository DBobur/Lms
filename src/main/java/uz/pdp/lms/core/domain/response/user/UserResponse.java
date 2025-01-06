package uz.pdp.lms.core.domain.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record UserResponse(Long id,
                           LocalDateTime createdDate,
                           LocalDateTime updatedDate,
                           String fullName,
                           String username,
                           String email,
                           String number,
                           String address,
                           LocalDate dateOfBirth,
                           Set<String> roles) {}

