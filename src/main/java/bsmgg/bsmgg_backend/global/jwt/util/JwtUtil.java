package bsmgg.bsmgg_backend.global.jwt.util;

import bsmgg.bsmgg_backend.domain.user.domain.UserRefreshToken;
import bsmgg.bsmgg_backend.domain.user.service.UserRefreshTokenService;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import bsmgg.bsmgg_backend.global.jwt.auth.AuthDetails;
import bsmgg.bsmgg_backend.global.jwt.auth.AuthDetailsService;
import bsmgg.bsmgg_backend.global.jwt.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final AuthDetailsService authDetailsService;
    private final UserRefreshTokenService userRefreshTokenService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.prefix}")
    private String prefix;

    @Value("${jwt.access-expiration-minutes}")
    private Integer accessExpMin;

    @Value("${jwt.refresh-expiration-hours}")
    private Integer refreshExpHour;

    public TokenDto createToken(UUID uuid, String gameName, String tagLine) {
        String accessToken = createAccessToken(uuid.toString());

        String refreshToken = createRefreshToken(uuid.toString());

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .gameName(gameName)
                .tagLine(tagLine)
                .build();
    }

    public String createAccessToken(String uuid) {
        return Jwts.builder()
                .claim("uuid", uuid)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .expiration(Date.from(Instant.now().plus(accessExpMin, ChronoUnit.MINUTES)))
                .compact();
    }

    public String createRefreshToken(String uuid) {
        return Jwts.builder()
                .claim("uuid", uuid)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .expiration(Date.from(Instant.now().plus(refreshExpHour, ChronoUnit.HOURS)))
                .compact();
    }

    public String recreateAccessToken(String refreshToken) {
        String uuid = extractUuid(refreshToken);
        int reissueLimit = refreshExpHour * 60 / accessExpMin;
        userRefreshTokenService.get(UUID.fromString(uuid), reissueLimit)
                .ifPresentOrElse(
                        UserRefreshToken::increaseReissueCount,
                        () -> {
                            throw new BSMGGException(ErrorCode.EXPIRED_REFRESH_TOKEN);
                        }
                );
        return createAccessToken(uuid);
    }

    public void validateRefreshToken(String refreshToken) {
        String uuid = extractUuid(refreshToken);
        int reissueLimit = refreshExpHour * 60 / accessExpMin;
        userRefreshTokenService.get(UUID.fromString(uuid), reissueLimit)
                .filter(userRefreshToken -> userRefreshToken.validateRefreshToken(refreshToken))
                .orElseThrow(() -> new BSMGGException(ErrorCode.EXPIRED_REFRESH_TOKEN));
    }

    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = (AuthDetails) authDetailsService.loadUserByUsername(extractUuid(token));
        return new UsernamePasswordAuthenticationToken(authDetails, token, Collections.emptyList());
    }

    public String extractUuid(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("uuid")
                .toString();
    }
}
