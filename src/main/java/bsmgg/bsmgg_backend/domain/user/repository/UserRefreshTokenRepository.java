package bsmgg.bsmgg_backend.domain.user.repository;

import bsmgg.bsmgg_backend.domain.user.domain.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, UUID> {
    Optional<UserRefreshToken> findByUserIdAndReissueCountLessThan(UUID id, int reissueCount);
}
