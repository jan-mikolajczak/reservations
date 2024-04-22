package com.jmiko.reservations.helper;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jmiko.reservations.constant.JWTUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.javacorner.admin.constant.JWTUtil.*;

@Component
public class JWTHelper {

    Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);

    public String generateAccessToken(String email, List<String> roles) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN))
                .withIssuer(JWTUtil.ISSUER)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public String generateRefreshToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_REFRESH_TOKEN))
                .withIssuer(JWTUtil.ISSUER)
                .sign(algorithm);
    }

    public String extractTokenFromHeaderIfExists(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(JWTUtil.BEARER_PREFIX)) {
            return authorizationHeader.substring(JWTUtil.BEARER_PREFIX.length());
        }
        return null;
    }

    public Map<String, String> getTokensMap(String jwtAccessToken, String jwtRefreshToken) {
        Map<String, String> idTokens = new HashMap<>();
        idTokens.put("accessToken", jwtAccessToken);
        idTokens.put("refreshToken", jwtRefreshToken);
        return idTokens;
    }
}
