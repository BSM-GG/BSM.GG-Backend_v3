package bsmgg.bsmgg_backend.domain.participant.domain;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String item1;
    @Column
    private String item2;
    @Column
    private String item3;
    @Column
    private String item4;
    @Column
    private String item5;
    @Column
    private String item6;
    @Column
    private String ward;
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
}
