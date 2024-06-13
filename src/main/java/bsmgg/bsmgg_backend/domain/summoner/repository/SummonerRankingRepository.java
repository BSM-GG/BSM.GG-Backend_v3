package bsmgg.bsmgg_backend.domain.summoner.repository;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.RankingDto;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.SummonerRanking;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import io.swagger.v3.oas.models.media.EmailSchema;
import leehj050211.bsmOauth.type.BsmUserRole;
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
                    .user(User.builder()
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
                            .build())
                    .build();

    public List<RankingDto> findPuuidWithRanking() {
        return jdbcTemplate.query("""
                select *, rank() over(
                    order by solo_point desc
                    ) as ranking
                from summoner s
                join bsmgg.user u on s.puuid = u.puuid
                """, mapper
        );
    }

    public List<SummonerRanking> findAllWithRanking() {
        return jdbcTemplate.query("""
                select *, rank() over(
                    order by solo_point desc
                    ) as ranking
                from summoner s
                join bsmgg.user u on s.puuid = u.puuid;
                """, rowMapper);
    }
}
