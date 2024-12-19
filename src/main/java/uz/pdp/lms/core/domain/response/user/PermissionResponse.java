package uz.pdp.lms.core.domain.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record PermissionResponse(Long id,
                                 String name,
                                 String description) {}
