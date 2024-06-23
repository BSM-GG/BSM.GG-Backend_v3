package bsmgg.bsmgg_backend.domain.summoner.controller.dto;

import bsmgg.bsmgg_backend.domain.participant.dto.Champion;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SummonerResponseDto {
    private String email;
    private Long code;
    private String name;
    private String nickname;
    private String role;
    private Boolean isGraduate;
    private Integer enrolledAt;
    private Integer grade;
    private Integer classNo;
    private Integer studentNo;
    private String puuid;
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
    private List<Champion> mostChampions;
    private Integer ranking;
    @Setter
    private Integer userCount;

    public SummonerResponseDto(Summoner summoner, Integer userCount, MappingService mappingService) {
        puuid = summoner.getPuuid();
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
        mostChampions = new ArrayList<>();
        mostChampions.add(new Champion(summoner.getMost1(), mappingService.getChamp(summoner.getMost1())));
        mostChampions.add(new Champion(summoner.getMost2(), mappingService.getChamp(summoner.getMost2())));
        mostChampions.add(new Champion(summoner.getMost3(), mappingService.getChamp(summoner.getMost3())));
        ranking = -1;
        this.userCount = userCount;
    }

    public void update(MappingService mappingService) {
        for (Champion champion : mostChampions) {
            champion.setName(mappingService.getChamp(champion.getId()));
        }
    }
}
