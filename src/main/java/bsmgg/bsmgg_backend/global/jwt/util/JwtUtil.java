package bsmgg.bsmgg_backend.global.jwt.util;

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

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final AuthDetailsService authDetailsService;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${PREFIX}")
    private String prefix;

    public TokenDto createToken(String puuid) {

        String accessToken = Jwts.builder()
                .claim("puuid", puuid)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    public String resolveJwt(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (bearer == null || !bearer.startsWith(prefix)) {
            return null;
        }

        return bearer.split(" ")[1].trim();
    }

    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = (AuthDetails) authDetailsService.loadUserByUsername(extractPuuid(token));
        return new UsernamePasswordAuthenticationToken(authDetails, token, Collections.emptyList());
    }

    public String extractPuuid(String token)     {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("puuid")
                .toString();
    }
}
