package bsmgg.bsmgg_backend.domain.user.controller.dto;

import girey.graduation_backend.domain.user.domain.User;

public record SignupRequestDto(
        String name,
        String email,
        String password
) {
    public User toEntity(String password) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

    }
}
