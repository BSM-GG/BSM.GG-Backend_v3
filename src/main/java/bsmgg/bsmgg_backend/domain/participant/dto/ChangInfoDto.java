package bsmgg.bsmgg_backend.domain.participant.dto;

import lombok.Builder;

@Builder
public record ChangInfoDto(
        Integer playedGames,
        Integer winGames,
        Integer loseGames,
        Integer kills,
        Integer deaths,
        Integer assists,
        Integer goldSpent,
        Integer goldEarned,
        Integer skillUse,
        Integer playTime,
        Integer spellUse,
        Integer damage,
        Integer gainDamage,
        Integer wardPlaced,
        Integer visionScore,
        Integer cs
) {
}
