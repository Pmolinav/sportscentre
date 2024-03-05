package net.pmolinav.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    //TODO: Get them from application.properties.
    private final static String ACCESS_TOKEN_SECRET = "c7eD5hYnJnVr3uFTh5WTG2XKj6qbBszvuztf8WbCcJY";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 12345L;

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    public static String createToken(String name, String username, String role) {

        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000L;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extraParams = new HashMap<>();
        extraParams.put("name", name);
        extraParams.put("role", role);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .addClaims(extraParams)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())).compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }

}
