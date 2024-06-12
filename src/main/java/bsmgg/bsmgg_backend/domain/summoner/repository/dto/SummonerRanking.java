package bsmgg.bsmgg_backend.domain.summoner.repository.dto;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import lombok.Builder;

@Builder
public record SummonerRanking(
        Summoner summoner,
        Integer ranking
) {
    public SummonerRanking(Summoner summoner) {
        this(
                summoner,
                -1
        );
    }
}