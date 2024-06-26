package bsmgg.bsmgg_backend.domain.participant.repository;

import bsmgg.bsmgg_backend.domain.match.repository.dto.MatchInfoDto;
import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import bsmgg.bsmgg_backend.domain.participant.dto.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void saveAll(List<Participant> participants) {
        String sql = """
                INSERT INTO participant
                (match_id,puuid,is_win,champion,champion_level,lane,team,kill_rate,kills,assists,deaths
                ,cs,damage,damage_rate,heal,earned_gold,spent_gold,vision_ward,sight_ward,vision_score
                ,skill_used,spell1,spell2,spell1used,spell2used,item1,item2,item3,item4,item5,item6,ward
                ,main_perk,main_perk_part1,main_perk_part2,main_perk_part3,sub_perk_style,sub_perk_part1
                ,sub_perk_part2,offense_perk,flex_perk,defense_perk)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                Participant participant = participants.get(i);
                ps.setString(1, participant.getMatch().getId());
                ps.setString(2, participant.getSummoner().getPuuid());
                ps.setBoolean(3, participant.getIsWin());
                ps.setString(4, participant.getChampion());
                ps.setLong(5, participant.getChampionLevel());
                ps.setInt(6, participant.getLane());
                ps.setString(7, participant.getTeam());
                ps.setInt(8, participant.getKillRate());
                ps.setInt(9, participant.getKills());
                ps.setInt(10, participant.getAssists());
                ps.setInt(11, participant.getDeaths());
                ps.setInt(12, participant.getCs());
                ps.setInt(13, participant.getDamage());
                ps.setInt(14, participant.getDamageRate());
                ps.setInt(15, participant.getHeal());
                ps.setInt(16, participant.getEarnedGold());
                ps.setInt(17, participant.getSpentGold());
                ps.setInt(18, participant.getVisionWard());
                ps.setInt(19, participant.getSightWard());
                ps.setInt(20, participant.getVisionScore());
                ps.setInt(21, participant.getSkillUsed());
                ps.setString(22, participant.getSpell1());
                ps.setString(23, participant.getSpell2());
                ps.setInt(24, participant.getSpell1Used());
                ps.setInt(25, participant.getSpell2Used());
                ps.setInt(26, participant.getItem1());
                ps.setInt(27, participant.getItem2());
                ps.setInt(28, participant.getItem3());
                ps.setInt(29, participant.getItem4());
                ps.setInt(30, participant.getItem5());
                ps.setInt(31, participant.getItem6());
                ps.setInt(32, participant.getWard());
                ps.setString(33, participant.getMainPerk());
                ps.setString(34, participant.getMainPerkPart1());
                ps.setString(35, participant.getMainPerkPart2());
                ps.setString(36, participant.getMainPerkPart3());
                ps.setString(37, participant.getSubPerkStyle());
                ps.setString(38, participant.getSubPerkPart1());
                ps.setString(39, participant.getSubPerkPart2());
                ps.setString(40, participant.getOffensePerk());
                ps.setString(41, participant.getFlexPerk());
                ps.setString(42, participant.getDefensePerk());
            }

            @Override
            public int getBatchSize() {
                return participants.size();
            }
        });
    }
}
