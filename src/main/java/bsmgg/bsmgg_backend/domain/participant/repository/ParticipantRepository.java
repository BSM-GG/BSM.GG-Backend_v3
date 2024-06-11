package bsmgg.bsmgg_backend.domain.participant.repository;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {

    /*@Query("""
            SELECT p.champion, count(p.champion) AS count
            FROM Participant p
            JOIN `Match`
            """)
    List<String> countAllBySummonerAndMatchIn(Summoner summoner, List<Match> matches);*/
}
