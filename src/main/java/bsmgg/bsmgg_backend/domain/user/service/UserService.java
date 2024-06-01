package bsmgg.bsmgg_backend.domain.user.service;

import bsmgg.bsmgg_backend.domain.user.controller.dto.LoginRequestDto;
import bsmgg.bsmgg_backend.domain.user.controller.dto.SignupRequestDto;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.repository.UserRefreshTokenRepository;
import bsmgg.bsmgg_backend.domain.user.repository.UserRepository;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import bsmgg.bsmgg_backend.global.error.exception.GradException;
import bsmgg.bsmgg_backend.global.jwt.dto.TokenDto;
import bsmgg.bsmgg_backend.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void create(SignupRequestDto dto) {
        if(userRepository.findByEmail(dto.email()).isPresent()) {
            throw new GradException(ErrorCode.USER_ALREADY_REGISTERED);
        }
        userRepository.save(User.from(dto, passwordEncoder));
    }

    @Transactional
    public TokenDto login(LoginRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.email());
        if(user.isEmpty())
            throw new GradException(ErrorCode.USER_NOT_FOUND);
        if (!passwordEncoder.matches(requestDto.password(), user.get().getPassword()))
            throw new GradException(ErrorCode.ILLEGAL_PASSWORD);

        return jwtUtil.createToken("puuid");
    }
}
