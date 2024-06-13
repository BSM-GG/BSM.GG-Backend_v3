package bsmgg.bsmgg_backend.domain.summoner.repository.dto;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import lombok.Builder;

@Builder
public record SummonerRanking(
        Summoner summoner,
        User user,
        Integer ranking
) {
    public SummonerRanking(Summoner summoner) {
        this(
                summoner,
                null,
                -1
        );
    }
}