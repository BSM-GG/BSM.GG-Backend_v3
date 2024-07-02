package bsmgg.bsmgg_backend.domain.summoner.repository;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {

    Summoner findByPuuid(String puuid);

    Optional<Summoner> findByGameNameAndTagLine(String gameName, String tagLine);

    @Query("""
            SELECT s.puuid
            FROM Summoner s
            WHERE s.puuid IN :puuids
            """)
    List<String> findIdsByPuuids(List<String> puuids);
}
