package com.vivacon.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${innovation.jwt.secret_salt}")
    private String jwtSecret;

    @Value("${innovation.jwt.jwt_validity}")
    private long jwtValidity;

    @Value("${innovation.jwt.jwt_issuer}")
    private String jwtIssuer;

    /**
     * This method is used to get username from JWT access token
     *
     * @param token
     * @return
     */
    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.get("username", String.class);
    }

    /**
     * This method is used to generate a new JWT access token
     *
     * @param username
     * @return String
     */
    public String generateAccessToken(String username) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(this.jwtIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidity))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * This method is used to validate a jwt access token
     *
     * @param token
     * @return
     */
    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
