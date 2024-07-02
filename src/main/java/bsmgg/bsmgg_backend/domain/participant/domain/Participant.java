package bsmgg.bsmgg_backend.domain.participant.domain;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.riot.dto.PerkStyleDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    @JsonBackReference
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puuid")
    @JsonBackReference
    private Summoner summoner;

    @Column
    private Boolean isWin;
    @Column
    private String champion;
    @Column
    private Integer championLevel;
    @Column
    private Integer lane;
    @Column
    private String team;
    @Column
    @Builder.Default
    private Integer killRate = 0;
    @Column
    private Integer kills;
    @Column
    private Integer assists;
    @Column
    private Integer deaths;
    @Column
    private Integer cs;
    @Column
    private Integer damage;
    @Column
    private Integer damageRate;
    @Column
    private Integer gainDamage;
    @Column
    private Integer heal;
    @Column
    private Integer earnedGold;
    @Column
    private Integer spentGold;
    @Column
    private Integer visionWard;
    @Column
    private Integer sightWard;
    @Column
    @Builder.Default
    private Integer visionScore = 0;
    @Column
    private Integer skillUsed;
    @Column
    private String spell1;
    @Column
    private String spell2;
    @Column
    private Integer spell1Used;
    @Column
    private Integer spell2Used;
    @Column
    private Integer item1;
    @Column
    private Integer item2;
    @Column
    private Integer item3;
    @Column
    private Integer item4;
    @Column
    private Integer item5;
    @Column
    private Integer item6;
    @Column
    private Integer ward;
    @Column
    private String mainPerk;
    @Column
    private String mainPerkPart1;
    @Column
    private String mainPerkPart2;
    @Column
    private String mainPerkPart3;
    @Column
    private String subPerkStyle;
    @Column
    private String subPerkPart1;
    @Column
    private String subPerkPart2;
    @Column
    private String offensePerk;
    @Column
    private String flexPerk;
    @Column
    private String defensePerk;

    public Participant(ParticipantDto dto, Match match, MappingService mappingService) {
        PerkStyleDto mainPerk = dto.getPerks().styles().stream()
                .findFirst().orElseThrow();
        PerkStyleDto subPerk = dto.getPerks().styles().stream()
                .filter(perk -> Objects.equals(perk.description(), "subStyle"))
                .findFirst().orElseThrow();
        this.match = match;
        this.summoner = Summoner.builder().puuid(dto.getPuuid()).build();
        this.isWin = dto.getWin();
        this.champion = Objects.equals(dto.getChampionName(), "FiddleSticks") ? "Fiddlesticks" : dto.getChampionName();
        this.championLevel = dto.getChampLevel();
        this.lane = dto.getRoleCnt();
        this.team = dto.getRoleCnt() < 5 ? "BLUE" : "RED";
        this.killRate = Math.round(dto.getChallenges().getKillParticipation() * 100);
        this.kills = dto.getKills();
        this.assists = dto.getAssists();
        this.deaths = dto.getDeaths();
        this.cs = dto.getTotalMinionsKilled() + dto.getTotalAllyJungleMinionsKilled() + dto.getTotalEnemyJungleMinionsKilled();
        this.damage = dto.getTotalDamageDealtToChampions();
        this.damageRate = Math.round((dto.getChallenges().getTeamDamagePercentage() != null ? dto.getChallenges().getTeamDamagePercentage() : 0) * 100);
        this.gainDamage = dto.getTotalDamageTaken();
        this.heal = dto.getTotalHeal();
        this.earnedGold = dto.getGoldEarned();
        this.spentGold = dto.getGoldSpent();
        this.sightWard = dto.getWardsPlaced();
        this.visionWard = dto.getVisionWardsBoughtInGame();
        this.visionScore = dto.getVisionScore();
        this.skillUsed = dto.getChallenges().getAbilityUses();
        this.spell1 = mappingService.getSpellId(dto.getSummoner1Id());
        this.spell2 = mappingService.getSpellId(dto.getSummoner2Id());
        this.spell1Used = dto.getSummoner1Casts();
        this.spell2Used = dto.getSummoner2Casts();
        this.item1 = dto.getItem0();
        this.item2 = dto.getItem1();
        this.item3 = dto.getItem2();
        this.item4 = dto.getItem3();
        this.item5 = dto.getItem4();
        this.item6 = dto.getItem5();
        this.ward = dto.getItem6() == 2056 ? 3340 : dto.getItem6();
        this.mainPerk = mappingService.getPerkName(mainPerk.selections().get(0).perk());
        this.mainPerkPart1 = mappingService.getPerkName(mainPerk.selections().get(1).perk());
        this.mainPerkPart2 = mappingService.getPerkName(mainPerk.selections().get(2).perk());
        this.mainPerkPart3 = mappingService.getPerkName(mainPerk.selections().get(3).perk());
        this.subPerkStyle = mappingService.getPerkName(subPerk.style());
        this.subPerkPart1 = mappingService.getPerkName(subPerk.selections().get(0).perk());
        this.subPerkPart2 = mappingService.getPerkName(subPerk.selections().get(1).perk());
        this.offensePerk = mappingService.getPerkName(dto.getPerks().statPerks().offense());
        this.flexPerk = mappingService.getPerkName(dto.getPerks().statPerks().flex());
        this.defensePerk = mappingService.getPerkName(dto.getPerks().statPerks().defense());
    }
}
