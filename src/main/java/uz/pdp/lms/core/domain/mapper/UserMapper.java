package uz.pdp.lms.core.domain.mapper;

import uz.pdp.lms.core.domain.request.user.UserRequest;
import uz.pdp.lms.core.domain.response.user.UserResponse;
import uz.pdp.lms.modules.user.entity.Role;
import uz.pdp.lms.modules.user.entity.User;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class UserMapper {
    public static User to(UserRequest request) {
        return User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .number(request.getNumber())
                .address(request.getAddress())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .build();
    }
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .number(user.getNumber())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}
