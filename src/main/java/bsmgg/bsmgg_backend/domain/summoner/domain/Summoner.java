package bsmgg.bsmgg_backend.domain.summoner.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

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
    private Long profileIcon;
    @Column(nullable = false)
    private Long level;
    @Column(nullable = false, length = 15)
    private String solo_tier = "";
    @Column(nullable = false)
    private Long solo_lp = 0L;
    @Column(nullable = false)
    private Long solo_wins = 0L;
    @Column(nullable = false)
    private Long solo_loses = 0L;
    @Column(nullable = false)
    private Long solo_point = 0L;
    @Column(nullable = false, length = 15)
    private String flex_tier = "";
    @Column(nullable = false)
    private Long flex_lp = 0L;
    @Column(nullable = false)
    private Long flex_wins = 0L;
    @Column(nullable = false)
    private Long flex_loses = 0L;
    @Column(nullable = false)
    private Long flex_point = 0L;
    @Column(nullable = false, length = 15)
    private String most1 = "";
    @Column(nullable = false, length = 15)
    private String most2 = "";
    @Column(nullable = false, length = 15)
    private String most3 = "";
    @Column(nullable = false)
    @Value("${riot.season-started-time}")
    private Long last_updated;

}
