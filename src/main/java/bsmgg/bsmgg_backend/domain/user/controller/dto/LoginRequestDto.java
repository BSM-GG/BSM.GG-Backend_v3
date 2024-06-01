package bsmgg.bsmgg_backend.domain.user.controller.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}
