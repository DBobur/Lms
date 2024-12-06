package uz.pdp.lms.core.domain.response.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uz.pdp.lms.modules.user.entity.Permission;
import uz.pdp.lms.modules.user.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record RoleResponse(Long id,
                           String name,
                           List<String> permissions) {}