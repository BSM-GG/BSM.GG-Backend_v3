package bsmgg.bsmgg_backend.domain.participant.repository;

import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {

    @Query("""
            SELECT p.champion
            FROM Participant p
            JOIN p.match m
            WHERE m.gameType = '솔로랭크'
            AND p.summoner.puuid = ?1
            GROUP BY p.champion
            ORDER BY COUNT(p.champion) desc
            """)
    List<String> findMostChampionsByPuuid(String puuid);

}
