package bsmgg.bsmgg_backend.domain.summoner.repository;

import bsmgg.bsmgg_backend.domain.summoner.repository.dto.RankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SummonerRankingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<RankingDto> mapper = (rs, rowNum) ->
            RankingDto.builder()
                    .puuid(rs.getString("puuid"))
                    .ranking(rs.getInt("ranking"))
                    .build();

    public List<RankingDto> findAllRanking() {
        return jdbcTemplate.query("""
                select s.puuid as puuid, rank() over(
                    order by solo_point desc
                    ) as ranking
                from summoner s
                join bsmgg.user u on s.puuid = u.puuid
                """, mapper
        );
    }
}
