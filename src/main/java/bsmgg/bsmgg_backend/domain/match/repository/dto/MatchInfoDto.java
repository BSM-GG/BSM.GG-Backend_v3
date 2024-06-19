package bsmgg.bsmgg_backend.domain.match.repository.dto;

import lombok.Builder;

@Builder
public record MatchInfoDto(
        String id,
        String gameType,
        Boolean isWin,
        Long gameStartedAt,
        Long gameEndAt,
        Long gameDuration
) {
}
