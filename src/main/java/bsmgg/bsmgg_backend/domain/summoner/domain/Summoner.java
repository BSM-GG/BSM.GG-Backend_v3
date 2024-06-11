package bsmgg.bsmgg_backend.domain.summoner.domain;

import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import bsmgg.bsmgg_backend.domain.riot.dto.LeagueEntryDTO;
import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Summoner {

    @Id
    @Column(length = 100)
    private String puuid;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "puuid")
    @JsonManagedReference
    private List<Participant> participant;

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
    @Builder.Default
    private Long revisionDate = 0L;
    @Column(length = 15)
    @Builder.Default
    private String soloTier = "";
    @Column
    @Builder.Default
    private Integer soloLp = 0;
    @Column
    @Builder.Default
    private Integer soloWins = 0;
    @Column
    @Builder.Default
    private Integer soloLoses = 0;
    @Column
    @Builder.Default
    private Integer soloPoint = 0;
    @Column(length = 15)
    @Builder.Default
    private String flexTier = "";
    @Column
    @Builder.Default
    private Integer flexLp = 0;
    @Column
    @Builder.Default
    private Integer flexWins = 0;
    @Column
    @Builder.Default
    private Integer flexLoses = 0;
    @Column
    @Builder.Default
    private Integer flexPoint = 0;
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
    @Setter
    private Long lastUpdated;

    public void updateAccount(RiotAccountDto account, SummonerDto summoner) {
        gameName = account.gameName();
        tagLine = account.tagLine();
        level = summoner.summonerLevel();
        profileIcon = summoner.profileIconId();
    }

    public void updateRank(LeagueEntryDTO solo, LeagueEntryDTO flex) {
        if(solo != null) {
            soloTier = String.format("%s %s", solo.tier(), solo.rank());
            soloLp = solo.leaguePoints();
            soloWins = solo.wins();
            soloLoses = solo.losses();
            soloPoint = getTierPoint(solo.tier()) + getRankPoint(solo.rank()) + soloLp;
        }
        if(flex != null) {
            flexTier = String.format("%s %s", flex.tier(), flex.rank());
            flexLp = flex.leaguePoints();
            flexWins = flex.wins();
            flexLoses = flex.losses();
            flexPoint = getTierPoint(flex.tier()) + getRankPoint(flex.rank()) + soloLp;
        }
    }

    private Integer getTierPoint(String tier) {
        return switch (tier) {
            case "IRON" -> 1000;
            case "BRONZE" -> 2000;
            case "SILVER" -> 3000;
            case "GOLD" -> 4000;
            case "PLATINUM" -> 5000;
            case "EMERALD" -> 6000;
            case "DIAMOND" -> 7000;
            case "MASTER", "GRAND MASTER", "CHALLENGER" -> 8000;
            default -> 0;
        };
    }

    private Integer getRankPoint(String rank) {
        return switch (rank) {
            case "I" -> 1000;
            case "II" -> 2000;
            case "III" -> 3000;
            case "IV" -> 4000;
            default -> 0;
        };
    }

    public void setMostChampions(List<String> mostChampions) {
        int len = mostChampions.size();
        if(len >= 3){
            most1 = mostChampions.get(0);
            most2 = mostChampions.get(1);
            most3 = mostChampions.get(2);
        }
        else if(len == 2){
            most1 = mostChampions.get(0);
            most2 = mostChampions.get(1);
        }
        else if(len == 1){
            most1 = mostChampions.get(0);
        }
    }
}
