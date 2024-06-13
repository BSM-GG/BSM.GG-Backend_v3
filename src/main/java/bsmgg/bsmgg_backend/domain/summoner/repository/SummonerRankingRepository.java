package bsmgg.bsmgg_backend.domain.summoner.repository;

import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SummonerRankingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<SummonerResponseDto> mapper = (rs, rowNum) ->
            SummonerResponseDto.builder()
                    .puuid(rs.getString("puuid"))
                    .gameName(rs.getString("game_name"))
                    .tagLine(rs.getString("tag_line"))
                    .profileIcon(rs.getInt("profile_icon"))
                    .level(rs.getLong("level"))
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
                    .mostChampions(new ArrayList<>() {
                        {
                            add(rs.getString("most1"));
                            add(rs.getString("most2"));
                            add(rs.getString("most3"));
                        }
                    })
                    .email(rs.getString("email"))
                    .code(rs.getLong("code"))
                    .nickname(rs.getString("nickname"))
                    .name(rs.getString("name"))
                    .role(rs.getString("role"))
                    .isGraduate(rs.getBoolean("is_graduate"))
                    .enrolledAt(rs.getInt("enrolled_at"))
                    .grade(rs.getInt("grade"))
                    .classNo(rs.getInt("class_no"))
                    .studentNo(rs.getInt("student_no"))
                    .ranking(rs.getInt("ranking"))
                    .build();

    public List<SummonerResponseDto> findAllWithRanking(int page) {
        String query = """
                select *, rank() over(
                    order by solo_point desc
                    ) as ranking
                from summoner s
                join bsmgg.user u on s.puuid = u.puuid
                """;
        if (page >= 0) {
            query += "limit "+page*10+", 10";
        }
        return jdbcTemplate.query(query, mapper);
    }
}
