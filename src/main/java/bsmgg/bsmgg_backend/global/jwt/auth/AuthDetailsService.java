package bsmgg.bsmgg_backend.global.jwt.auth;

import bsmgg.bsmgg_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUuid(UUID.fromString(username))
                .map(AuthDetails::new)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
    }
}
