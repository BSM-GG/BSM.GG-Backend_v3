package bsmgg.bsmgg_backend.domain.user.repository;

import bsmgg.bsmgg_backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    Optional<User> findBySummonerPuuid(String puuid);
}
