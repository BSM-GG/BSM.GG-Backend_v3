package bsmgg.bsmgg_backend.domain.match.repository;

import bsmgg.bsmgg_backend.domain.match.repository.dto.MatchInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<MatchInfoDto> rowMapper = (rs, rowNum) ->
            MatchInfoDto.builder()
                    .id(rs.getString("id"))
                    .gameType(rs.getString("game_type"))
                    .isWin(rs.getBoolean("is_win"))
                    .gameStartedAt(rs.getLong("game_started_at"))
                    .gameDuration(rs.getLong("game_duration"))
                    .gameEndAt(rs.getLong("game_end_at"))
                    .build();

    public List<MatchInfoDto> findWithIsWin(String puuid, String gameType, Integer page) {
        gameType = "%" + gameType + "%";
        String query = """
                select m.id, game_type, is_win, game_started_at, game_duration, game_end_at
                from matches m
                join bsmgg.participant p on p.match_id = m.id
                where game_type != '아레나'
                and game_type like ?
                and p.puuid = ?
                order by m.game_started_at desc
                limit ?, 10
                """;
        return jdbcTemplate.query(query, rowMapper, gameType, puuid, page);
    }
}
