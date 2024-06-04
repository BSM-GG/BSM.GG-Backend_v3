package bsmgg.bsmgg_backend.domain.user.service;

import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;

    public User getUser() {
        UUID id = UUID.fromString(getUserUuid());
        return userRepository.findById(id).orElseThrow();
    }

    public String getUserUuid() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
