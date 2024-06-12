package bsmgg.bsmgg_backend.domain.summoner.repository;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.SummonerRanking;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    Summoner findByPuuid(String puuid);

    Optional<Summoner> findByGameNameAndTagLine(String gameName, String tagLine);

    @Query(value = """
            SELECT Summoner, RANK() OVER (
                PARTITION BY puuid
                ORDER BY soloPoint desc
            ) AS ranking
            FROM Summoner
            WHERE puuid LIKE :puuid
            """)
    SummonerRanking findAllWithRank(String puuid);

    @Query(value = """
            select Summoner, rank() over(
                partition by puuid
                order by soloPoint desc
                ) as ranking
            from Summoner
            where gameName like ?1
            and tagLine like ?2
            """)
    Optional<SummonerRanking> findAllWithRank(String gameName, String tagLine);
}
