
package dev.reja.ecom.userService.utils;


import dev.reja.ecom.userService.exceptions.RandomException;
import dev.reja.ecom.userService.exceptions.UnauthorizedEsception;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;

    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;


    private String SECRET_KEY = "MySecretKeyForJWTMySecretKeyForJWT";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) throws UnauthorizedEsception {
        try{
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedEsception("Token expired");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedEsception("Malformed token");
        } catch (SignatureException e) {
            throw new UnauthorizedEsception("Invalid signature");
        } catch (JwtException e) {
            throw new UnauthorizedEsception("Invalid token");
        }
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION )) // 5 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }
    public String generateRefreshToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION)) // 5 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }

    public String getAccessTokenByRefreshToken(String refreshToken) {
        try {
            Claims claims = extractAllClaims(refreshToken);

            // validate refresh token is not expired
            if (claims.getExpiration().before(new Date())) {
                throw new UnauthorizedEsception("Refresh token expired");
            }

            String username = claims.getSubject();

            Map<String, Object> newClaims = new HashMap<>();

            // create a new short-lived access token
            return createToken(newClaims, username);

        } catch (ExpiredJwtException e) {
            throw new UnauthorizedEsception("Refresh token expired");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedEsception("Malformed refresh token");
        } catch (SignatureException e) {
            throw new UnauthorizedEsception("Invalid refresh token signature");
        } catch (JwtException e) {
            throw new UnauthorizedEsception("Invalid refresh token");
        }
    }


    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


}
