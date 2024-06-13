package bsmgg.bsmgg_backend.domain.summoner.controller.dto;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record SummonerResponseDto(
        String email,
        Long code,
        String name,
        String nickname,
        String role,
        Boolean isGraduate,
        Integer enrolledAt,
        Integer grade,
        Integer classNo,
        Integer studentNo,
        String puuid,
        String gameName,
        String tagLine,
        Integer profileIcon,
        Long level,
        String soloTier,
        Integer soloLp,
        Integer soloWins,
        Integer soloLoses,
        String flexTier,
        Integer flexLp,
        Integer flexWins,
        Integer flexLoses,
        List<String> mostChampions,
        Integer soloPoint,
        Integer flexPoint,
        Integer ranking
) {
    public SummonerResponseDto(Summoner summoner) {
        this(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                summoner.getPuuid(),
                summoner.getGameName(),
                summoner.getTagLine(),
                summoner.getProfileIcon(),
                summoner.getLevel(),
                summoner.getSoloTier(),
                summoner.getSoloLp(),
                summoner.getSoloWins(),
                summoner.getSoloLoses(),
                summoner.getFlexTier(),
                summoner.getFlexLp(),
                summoner.getFlexWins(),
                summoner.getFlexLoses(),
                new ArrayList<>() {
                    {
                        add(summoner.getMost1());
                        add(summoner.getMost2());
                        add(summoner.getMost3());
                    }
                },
                summoner.getSoloPoint(),
                summoner.getFlexPoint(),
                -1
        );
    }
}
