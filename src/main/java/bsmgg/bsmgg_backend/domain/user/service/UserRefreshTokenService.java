package bsmgg.bsmgg_backend.domain.user.service;

import bsmgg.bsmgg_backend.domain.user.domain.UserRefreshToken;
import bsmgg.bsmgg_backend.domain.user.repository.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRefreshTokenService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    public Optional<UserRefreshToken> get(UUID id, int reissueCount) {
        return userRefreshTokenRepository.findByUserIdAndReissueCountLessThan(id, reissueCount);
    }

    public Optional<UserRefreshToken> getById(UUID id) {
        return userRefreshTokenRepository.findById(id);
    }

    public void save(UserRefreshToken userRefreshToken) {
        userRefreshTokenRepository.save(userRefreshToken);
    }

}
