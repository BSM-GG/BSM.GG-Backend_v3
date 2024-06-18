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
        Integer soloPoint,
        String flexTier,
        Integer flexLp,
        Integer flexWins,
        Integer flexLoses,
        Integer flexPoint,
        List<String> mostChampions,
        Integer ranking,
        Integer userCount
) {
    public SummonerResponseDto(Summoner summoner, Integer userCount) {
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
                summoner.getSoloPoint(),
                summoner.getFlexTier(),
                summoner.getFlexLp(),
                summoner.getFlexWins(),
                summoner.getFlexLoses(),
                summoner.getFlexPoint(),
                new ArrayList<>() {
                    {
                        add(summoner.getMost1());
                        add(summoner.getMost2());
                        add(summoner.getMost3());
                    }
                },
                -1,
                userCount
        );
    }
}
