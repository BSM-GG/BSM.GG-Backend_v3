package bsmgg.bsmgg_backend.global.jwt.filter;

import bsmgg.bsmgg_backend.global.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (accessToken != null) {
                accessToken = accessToken.replace("Bearer ", "").trim();
                Authentication authentication = jwtUtil.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            reissueAccessToken(request, response, e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claim string is empty.");
        }

        filterChain.doFilter(request, response);
    }

    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken == null) {
                throw exception;
            }
            jwtUtil.validateRefreshToken(refreshToken);
            String newAccessToken = jwtUtil.recreateAccessToken(refreshToken);
            Authentication authentication = jwtUtil.getAuthentication(newAccessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.setHeader("New-Authorization", newAccessToken);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
    }
}
