package bsmgg.bsmgg_backend.domain.user.service;

import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;

    public User getUser() {
        String stringId = getUserUuid();
        if (Objects.equals(stringId, "anonymousUser")) {
            return null;
        }
        UUID id = UUID.fromString(getUserUuid());
        return userRepository.findById(id).orElse(null);
    }

    public String getUserUuid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Integer getUserCount() {
        return userRepository.findCount();
    }
}
