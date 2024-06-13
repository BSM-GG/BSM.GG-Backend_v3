package bsmgg.bsmgg_backend.domain.summoner.controller.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SummonerRankingResponseDto(
        List<SummonerResponseDto> summonerResponseDto,
        Integer page
) {
}
