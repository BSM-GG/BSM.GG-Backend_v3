package bsmgg.bsmgg_backend.domain.match.repository;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
}
