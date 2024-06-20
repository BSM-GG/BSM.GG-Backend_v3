package bsmgg.bsmgg_backend.domain.participant.repository;

import bsmgg.bsmgg_backend.domain.match.repository.dto.MatchInfoDto;
import bsmgg.bsmgg_backend.domain.participant.dto.*;
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
    private final RowMapper<ParticipantResponseDto> participantRowMapper = (rs, rowNum) ->
            ParticipantResponseDto.builder()
                    .gameName(rs.getString("game_name"))
                    .tagLine(rs.getString("tag_line"))
                    .soloTier(rs.getString("solo_tier"))
                    .soloPoint(rs.getInt("solo_point"))
                    .flexTier(rs.getString("flex_tier"))
                    .flexPoint(rs.getInt("flex_point"))
                    .level(rs.getInt("level"))
                    .champion(new Champion(rs.getString("champion")))
                    .championLevel(rs.getInt("champion_level"))
                    .lane(rs.getInt("lane"))
                    .team(rs.getString("team"))
                    .spell1(new Spell(rs.getString("spell1")))
                    .spell2(new Spell(rs.getString("spell2")))
                    .mainPerk(new Perk(rs.getString("main_perk")))
                    .subPerk(new Perk(rs.getString("sub_perk_style")))
                    .killRate(rs.getInt("kill_rate"))
                    .kills(rs.getInt("kills"))
                    .assists(rs.getInt("assists"))
                    .deaths(rs.getInt("deaths"))
                    .damage(rs.getInt("damage"))
                    .gainDamage(rs.getInt("gain_damage"))
                    .cs(rs.getInt("cs"))
                    .visionScore(rs.getInt("vision_score"))
                    .sightWard(rs.getInt("sight_ward"))
                    .visionWard(rs.getInt("vision_ward"))
                    .items(new ArrayList<>() {
                        {
                            add(new Item(rs.getInt("item1")));
                            add(new Item(rs.getInt("item2")));
                            add(new Item(rs.getInt("item3")));
                            add(new Item(rs.getInt("item4")));
                            add(new Item(rs.getInt("item5")));
                            add(new Item(rs.getInt("item6")));
                        }
                    })
                    .ward(new Item(rs.getInt("ward")))
                    .build();
    private final RowMapper<ChangInfoDto> changRowMapper = (rs, rowNum) ->
            ChangInfoDto.builder()
                    .playedGames(rs.getInt("playedGames"))
                    .winGames(rs.getInt("winGames"))
                    .loseGames(rs.getInt("loseGames"))
                    .kills(rs.getInt("kills"))
                    .assists(rs.getInt("assists"))
                    .deaths(rs.getInt("deaths"))
                    .goldEarned(rs.getInt("goldEarned"))
                    .goldSpent(rs.getInt("goldSpent"))
                    .skillUse(rs.getInt("skillUse"))
                    .playTime(rs.getInt("playTime"))
                    .spellUse(rs.getInt("spellUse"))
                    .damage(rs.getInt("damage"))
                    .gainDamage(rs.getInt("gainDamage"))
                    .wardPlaced(rs.getInt("wardPlaced"))
                    .visionScore(rs.getInt("visionScore"))
                    .cs(rs.getInt("cs"))
                    .build();


    public List<ParticipantResponseDto> findAllByMatchIn(MatchInfoDto match) {
        String query = """
                SELECT *
                FROM participant p
                JOIN bsmgg.summoner s ON s.puuid = p.puuid
                WHERE match_id = ?
                """;
        return jdbcTemplate.query(query, participantRowMapper, match.id());
    }

    public ChangInfoDto findChangByPuuid(String puuid, long prevWeekStart, long prevWeekEnd) {
        String query = """
                select p.puuid, count(p.puuid) as playedGames,
                sum(p.is_win = 1) as winGames,
                sum(p.is_win = 0) as loseGames,
                sum(kills) as kills,
                sum(assists) as assists,
                sum(deaths) as deaths,
                sum(earned_gold) as goldEarned,
                sum(spent_gold) as goldSpent,
                sum(skill_used) as skillUse,
                sum(game_duration) as playTime,
                sum(spell1used) + sum(spell2used) as spellUse,
                sum(damage) as damage,
                sum(gain_damage) as gainDamage,
                sum(sight_ward) as wardPlaced,
                sum(vision_score) as visionScore,
                sum(cs) as cs
                from participant p
                join bsmgg.matches m on m.id = p.match_id
                where p.puuid = ?
                and game_started_at >= ?
                and game_end_at <= ?
                and game_type != '아레나'
                group by p.puuid
                """;
        return jdbcTemplate.queryForObject(query, changRowMapper, puuid, prevWeekStart, prevWeekEnd);
    }
}
