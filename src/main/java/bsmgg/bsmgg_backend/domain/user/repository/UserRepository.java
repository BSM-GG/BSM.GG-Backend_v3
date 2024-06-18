package bsmgg.bsmgg_backend.domain.user.repository;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Query("""
            SELECT COUNT(u)
            FROM User u
            JOIN u.summoner s
            """)
    Integer findCount();

    User findBySummoner(Summoner summoner);
}
