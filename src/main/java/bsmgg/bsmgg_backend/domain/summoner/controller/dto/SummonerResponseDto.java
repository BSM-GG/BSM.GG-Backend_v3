package bsmgg.bsmgg_backend.domain.summoner.controller.dto;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.user.domain.User;

import java.util.List;

public record SummonerResponseDto(
        String email,
        Long code,
        String name,
        String nickname,
        Boolean isGraduate,
        Integer enrolledAt,
        Integer grade,
        Integer classNo,
        Integer studentNo,
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
    public SummonerResponseDto(User user, Summoner summoner, List<String> mostChampions, Integer ranking) {
        this(
                user.getEmail(),
                user.getCode(),
                user.getName(),
                user.getNickname(),
                user.getIsGraduate(),
                user.getEnrolledAt(),
                user.getGrade(),
                user.getClassNo(),
                user.getStudentNo(),
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
                mostChampions,
                summoner.getSoloPoint(),
                summoner.getFlexPoint(),
                ranking
        );
    }
}
