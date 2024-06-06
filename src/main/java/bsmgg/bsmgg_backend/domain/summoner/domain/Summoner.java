package bsmgg.bsmgg_backend.domain.summoner.domain;

import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Summoner {

    @Id
    @Column(length = 100)
    private String puuid;
    @Column(unique = true, nullable = false, length = 100)
    private String riotId;

    @Column(nullable = false, length = 50)
    private String gameName;
    @Column(nullable = false, length = 20)
    private String tagLine;
    @Column(nullable = false)
    private Integer profileIcon;
    @Column(nullable = false)
    private Long level;
    @Column(nullable = false)
    private Long revisionDate;
    @Column(length = 15)
    @Builder.Default
    private String solo_tier = "";
    @Column
    @Builder.Default
    private Long solo_lp = 0L;
    @Column
    @Builder.Default
    private Long solo_wins = 0L;
    @Column
    @Builder.Default
    private Long solo_loses = 0L;
    @Column
    @Builder.Default
    private Long solo_point = 0L;
    @Column(length = 15)
    @Builder.Default
    private String flex_tier = "";
    @Column
    @Builder.Default
    private Long flex_lp = 0L;
    @Column
    @Builder.Default
    private Long flex_wins = 0L;
    @Column
    @Builder.Default
    private Long flex_loses = 0L;
    @Column
    @Builder.Default
    private Long flex_point = 0L;
    @Column(length = 15)
    @Builder.Default
    private String most1 = "";
    @Column( length = 15)
    @Builder.Default
    private String most2 = "";
    @Column(length = 15)
    @Builder.Default
    private String most3 = "";
    @Column(nullable = false)
    private Long last_updated;

    public void updateAccount(RiotAccountDto account, SummonerDto summoner) {
        gameName = account.gameName();
        tagLine = account.tagLine();
        level = summoner.summonerLevel();
        profileIcon = summoner.profileIconId();
    }
}
