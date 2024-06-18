package bsmgg.bsmgg_backend.domain.match.repository;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {

    @Query("""
            SELECT m
            FROM Participant p
            JOIN p.match m
            where p.summoner.puuid = :uuid
            AND m.gameType != '아레나'
            """)
    List<Match> findAllBySummoner(String uuid, Pageable pageable);

    @Query("""
            SELECT p.isWin
            FROM Participant p
            JOIN p.match m
            where p.summoner.puuid = :uuid
            AND m.gameType != '아레나'
            """)
    List<Boolean> findIsWinBySummoner(String uuid, Pageable pageable);
}
