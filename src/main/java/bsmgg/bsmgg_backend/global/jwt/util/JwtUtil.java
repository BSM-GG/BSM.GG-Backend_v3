package bsmgg.bsmgg_backend.global.jwt.util;

import bsmgg.bsmgg_backend.global.jwt.auth.AuthDetails;
import bsmgg.bsmgg_backend.global.jwt.auth.AuthDetailsService;
import bsmgg.bsmgg_backend.global.jwt.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final AuthDetailsService authDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public TokenDto createToken(UUID uuid, String gameName, String tagLine) {
        String accessToken = createAccessToken(uuid.toString());

        return TokenDto.builder()
                .accessToken(accessToken)
                .gameName(gameName)
                .tagLine(tagLine)
                .build();
    }

    public String createAccessToken(String uuid) {
        return Jwts.builder()
                .claim("uuid", uuid)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .compact();
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
