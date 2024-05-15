package com.justdo.plug.blog.global.utils;

import static com.justdo.plug.blog.global.utils.JwtProperties.HEADER_AUTHORIZATION;
import static com.justdo.plug.blog.global.utils.JwtProperties.MEMBER_ID;
import static com.justdo.plug.blog.global.utils.JwtProperties.TOKEN_PREFIX;
import static com.justdo.plug.blog.global.utils.JwtProperties.TOKEN_SPLIT;

import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final SecretKey key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        String keyEncoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyEncoded.getBytes());
    }

    public Long getUserIdFromToken(HttpServletRequest request) {

        String accessToken = parseToken(request);

        return validateToken(accessToken);
    }

    private String parseToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (bearerToken == null || !bearerToken.startsWith(TOKEN_PREFIX)) {
            throw new ApiException(ErrorStatus._JWT_NOT_FOUND);
        } else {
            return bearerToken.substring(TOKEN_SPLIT);
        }
    }

    public Long validateToken(String accessToken) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken)
                    .getPayload().get(MEMBER_ID, Long.class);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT 토큰이 잘못되었습니다.");
        }
    }
}
