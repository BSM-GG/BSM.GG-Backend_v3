package bsmgg.bsmgg_backend.domain.summoner.repository;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.SummonerRanking;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SummonerRankingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SummonerRanking> rowMapper = (rs, rowNum) ->
            SummonerRanking.builder()
                    .summoner(Summoner.builder()
                            .puuid(rs.getString("puuid"))
                            .riotId(rs.getString("riot_id"))
                            .gameName(rs.getString("game_name"))
                            .tagLine(rs.getString("tag_line"))
                            .profileIcon(rs.getInt("profile_icon"))
                            .level(rs.getLong("level"))
                            .revisionDate(rs.getLong("revision_date"))
                            .soloTier(rs.getString("solo_tier"))
                            .soloLp(rs.getInt("solo_lp"))
                            .soloWins(rs.getInt("solo_wins"))
                            .soloLoses(rs.getInt("solo_loses"))
                            .soloPoint(rs.getInt("solo_point"))
                            .flexTier(rs.getString("flex_tier"))
                            .flexLp(rs.getInt("flex_lp"))
                            .flexWins(rs.getInt("flex_wins"))
                            .flexLoses(rs.getInt("flex_loses"))
                            .flexPoint(rs.getInt("flex_point"))
                            .most1(rs.getString("most1"))
                            .most2(rs.getString("most2"))
                            .most3(rs.getString("most3"))
                            .lastUpdated(rs.getLong("last_updated"))
                            .build())
                    .ranking(rs.getInt("ranking"))
                    .build();

    private String url = """
            select *, rank() over(
                partition by puuid
                order by solo_point desc
                ) as ranking
             from summoner s
            """;

    public SummonerRanking findAllWithRank(String puuid) {
        return jdbcTemplate.queryForObject(
                url + " where puuid like ?",
                rowMapper, puuid);
    }

    public Optional<SummonerRanking> findAllWithRank(String gameName, String tagLine) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                url + " where game_name like ? and tag_line like ?",
                rowMapper, gameName, tagLine));
    }
}
