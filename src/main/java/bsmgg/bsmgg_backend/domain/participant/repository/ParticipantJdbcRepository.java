package bsmgg.bsmgg_backend.domain.participant.repository;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.participant.dto.ParticipantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParticipantJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ParticipantResponseDto> rowMapper = (rs, rowNum) ->
            ParticipantResponseDto.builder()
                    .gameName(rs.getString("game_name"))
                    .tagLine(rs.getString("tag_line"))
                    .soloTier(rs.getString("solo_tier"))
                    .soloPoint(rs.getInt("solo_point"))
                    .flexTier(rs.getString("flex_tier"))
                    .flexPoint(rs.getInt("flex_point"))
                    .level(rs.getInt("level"))
                    .champion(rs.getString("champion"))
                    .championLevel(rs.getInt("champion_level"))
                    .lane(rs.getString("lane"))
                    .team(rs.getString("team"))
                    .spell1(rs.getString("spell1"))
                    .spell2(rs.getString("spell2"))
                    .mainPerk(rs.getString("main_perk"))
                    .subPerk(rs.getString("sub_perk_style"))
                    .kills(rs.getInt("kills"))
                    .assists(rs.getInt("assists"))
                    .deaths(rs.getInt("deaths"))
                    .damage(rs.getInt("damage"))
                    .gainDamage(rs.getInt("gain_damage"))
                    .cs(rs.getInt("cs"))
                    .sightWard(rs.getInt("sight_ward"))
                    .visionWard(rs.getInt("vision_ward"))
                    .items(new ArrayList<>() {
                        {
                            add(rs.getString("item1"));
                            add(rs.getString("item2"));
                            add(rs.getString("item3"));
                            add(rs.getString("item4"));
                            add(rs.getString("item5"));
                            add(rs.getString("item6"));
                        }
                    })
                    .ward(rs.getString("ward"))
                    .build();

    public List<ParticipantResponseDto> findAllByMatchIn(Match match) {
        String query = """
                SELECT *
                FROM participant p
                JOIN bsmgg.summoner s ON s.puuid = p.puuid
                WHERE match_id = ?
                """;
        return jdbcTemplate.query(query, rowMapper, match.getId());
    }
}
