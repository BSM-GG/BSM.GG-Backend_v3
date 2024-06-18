package bsmgg.bsmgg_backend.domain.summoner.controller.dto;

import bsmgg.bsmgg_backend.domain.participant.dto.ChangInfoDto;

public record ChangResponseDto(
        SummonerResponseDto summonerInfo,
        ChangInfoDto changInfo
) {
}
