package bsmgg.bsmgg_backend.domain.summoner.repository.dto;

import lombok.Builder;

@Builder
public record RankingDto(
        String puuid,
        Integer ranking
) {
}
