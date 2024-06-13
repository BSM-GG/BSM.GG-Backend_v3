package bsmgg.bsmgg_backend.domain.summoner.controller.dto;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.user.domain.User;

import java.util.List;

public class SummonerResponseDto {
    private String email = "";
    private Long code = 0L;
    private String name = "";
    private String nickname = "";
    private Boolean isGraduate = false;
    private Integer enrolledAt =0;
    private Integer grade = 0;
    private Integer classNo = 0;
    private Integer studentNo = 0;
    private String gameName;
    private String tagLine;
    private Integer profileIcon;
    private Long level;
    private String soloTier;
    private Integer soloLp;
    private Integer soloWins;
    private Integer soloLoses;
    private Integer soloPoint;
    private String flexTier;
    private Integer flexLp;
    private Integer flexWins;
    private Integer flexLoses;
    private Integer flexPoint;
    private List<String> mostChampions;
    private Integer ranking;

    public SummonerResponseDto(User user, Summoner summoner, List<String> mostChampions, Integer ranking) {
        if (user != null) {
            email = user.getEmail();
            code = user.getCode();
            name = user.getName();
            nickname = user.getNickname();
            isGraduate = user.getIsGraduate();
            enrolledAt = user.getEnrolledAt();
            grade = user.getGrade();
            classNo = user.getClassNo();
            studentNo = user.getStudentNo();
        }
        gameName = summoner.getGameName();
        tagLine = summoner.getTagLine();
        profileIcon = summoner.getProfileIcon();
        level = summoner.getLevel();
        soloTier = summoner.getSoloTier();
        soloLp = summoner.getSoloLp();
        soloWins = summoner.getSoloWins();
        soloLoses = summoner.getSoloLoses();
        soloPoint = summoner.getSoloPoint();
        flexTier = summoner.getFlexTier();
        flexLp = summoner.getFlexLp();
        flexWins = summoner.getFlexWins();
        flexLoses = summoner.getFlexLoses();
        flexPoint = summoner.getFlexPoint();
        this.mostChampions = mostChampions;
        this.ranking = ranking;
    }
}
