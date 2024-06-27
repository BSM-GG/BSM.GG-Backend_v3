package bsmgg.bsmgg_backend.domain.match.repository;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {

    @Query("""
            SELECT m.id
            FROM Match m
            WHERE m.id in :ids
            """)
    List<String> findIdsInIds(List<String> ids);
}
